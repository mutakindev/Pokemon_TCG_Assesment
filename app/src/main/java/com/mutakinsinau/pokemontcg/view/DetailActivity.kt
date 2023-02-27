package com.mutakinsinau.pokemontcg.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mutakinsinau.pokemontcg.R.*
import com.mutakinsinau.pokemontcg.data.network.dto.Pokemon
import com.mutakinsinau.pokemontcg.databinding.ActivityDetailBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val pokemon: Pokemon by lazy {
        val pokemon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(pokemonKey, Pokemon::class.java) as Pokemon
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(pokemonKey)!!
        }

        pokemon
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        Glide.with(this).load(pokemon.images.large)
            .override(720, 900)
            .into(binding.ivLarge)
        binding.tvName.text = pokemon.name
        binding.tvType.text = pokemon.types.joinToString(", ")
        binding.btnDownload.setOnClickListener {
            downloadImage(pokemon.images.large)
        }
    }


    fun downloadImage(imageURL: String) {
        if (!verifyPermissions()) {
            return
        }
        val dirPath: String = Environment.getExternalStorageDirectory()
            .absolutePath + "/" + getString(string.app_name) + "/"
        val dir = File(dirPath)
        val fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1)
        Glide.with(this)
            .load(imageURL)
            .override(720, 1024)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    Toast.makeText(this@DetailActivity, "Saving Image...", Toast.LENGTH_SHORT)
                        .show()
                    saveImage(bitmap, dir, fileName)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    Toast.makeText(
                        this@DetailActivity,
                        "Failed to Download Image! Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun verifyPermissions(): Boolean {

        // This will return the current Status
        val permissionExternalMemory =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions(this, permissions, 1)
            return false
        }
        return true
    }

    private fun saveImage(image: Bitmap, storageDir: File, imageFileName: String) {
        var successDirCreated = false
        if (!storageDir.exists()) {
            successDirCreated = storageDir.mkdir()
        }
        if (successDirCreated) {
            val imageFile = File(storageDir, imageFileName)
            val savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                Toast.makeText(this@DetailActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@DetailActivity, "Error while saving image!", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        } else {
           Toast.makeText(this, "Failed to make folder!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val pokemonKey = "pokemon_key"
    }
}