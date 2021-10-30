package com.example.dados

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dados.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    private lateinit var activityConfigBinding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityConfigBinding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(activityConfigBinding.root)

        activityConfigBinding.salvarBt.setOnClickListener {
            val numeroDados: Int =
                (activityConfigBinding.numeroDadosSp.selectedView as TextView).text.toString()
                    .toInt()
            val numeroFaces: Int = activityConfigBinding.numeroFacesEt.text.toString().toInt()
            val configuracao: Configuracao = Configuracao(numeroDados, numeroFaces)
            val retornoIntent: Intent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, configuracao)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}