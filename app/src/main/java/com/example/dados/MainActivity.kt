package com.example.dados

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>

    private var range: IntRange = 1..6
    private var numeroDados: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadoBt.setOnClickListener {
            val resultado: Int = geradorRandomico.nextInt(1, 6)
            "A face sortiada foi $resultado".also { activityMainBinding.resultadoTv.text = it }
            val nomeImage: String = "dice_$resultado"
            activityMainBinding.resultadoIv.setImageResource(
                resources.getIdentifier(nomeImage, "mipmap", packageName)
            )
        }

        activityMainBinding.jogarDadoBt.setOnClickListener {
            val resultado1: Int = geradorRandomico.nextInt(range)
            val resultado2: Int = geradorRandomico.nextInt(range)
            val nomeImagem1: String = "dice_${resultado1}"
            val nomeImagem2: String = "dice_${resultado2}"

            configValueDice(nomeImagem1, nomeImagem2, numeroDados)
            isDiceTwoVisible(numeroDados)
            isFaceVisible(range)
            showTextMessageResult(numeroDados, resultado1, resultado2)
        }

        settingsActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    if (result.data != null) {
                        val configuracao: Configuracao? =
                            result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)
                        if (configuracao != null) {
                            range = IntRange(1, configuracao.numeroFaces)
                            numeroDados = configuracao.numeroDados
                        }
                    }
                }
            }
    }

    private fun isDiceTwoVisible(numeroDados: Int) {
        activityMainBinding.resultadoIv.visibility = View.VISIBLE
        if (numeroDados == 2) {
            activityMainBinding.resultadoIv2.visibility = View.VISIBLE
        } else {
            activityMainBinding.resultadoIv2.visibility = View.GONE
        }
    }

    private fun isFaceVisible(range: IntRange) {
        if (range.last > 6) {
            activityMainBinding.resultadoIv.visibility = View.GONE
            activityMainBinding.resultadoIv2.visibility = View.GONE
        }
    }

    private fun showTextMessageResult(numeroDados: Int, r1: Int, r2: Int) {
        if (numeroDados == 2) {
            activityMainBinding.resultadoTv.text = "Sorteado: $r1 e $r2"
        } else {
            "Sorteado: $r1".also { activityMainBinding.resultadoTv.text = it }
        }
    }

    private fun configValueDice(nomeImagem1: String, img1: String, numDados: Int) {
        if (numDados == 2) {
            activityMainBinding.resultadoIv2.setImageResource(
                resources.getIdentifier(img1, "mipmap", packageName)
            )
        }
        activityMainBinding.resultadoIv.setImageResource(
            resources.getIdentifier(nomeImagem1, "mipmap", packageName)
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMi) {
            val settingsIntent = Intent(this, ConfigActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}
