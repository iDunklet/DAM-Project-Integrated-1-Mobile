package com.example.myapplication

import android.widget.Button

object MetodosUniversal {
    private val DRAWABLE_NORMAL = R.drawable.edit_text_radius
    private val DRAWABLE_SELECCIONADO = R.drawable.boton_verde

    fun setSelectionStyle(
        botonPresionado: Button,
        valor: Int,
        botonesGrupo: List<Button>,
        targetVariable: Int,
        onUpdateData: (target: Int, value: Int) -> Unit
                         )
    {

        resetButtonStyles(botonesGrupo, DRAWABLE_NORMAL)
        highlightButton(botonPresionado, DRAWABLE_SELECCIONADO)

        onUpdateData(targetVariable, valor)
    }

    private fun resetButtonStyles(botones: List<Button>, normalStyle: Int) {
        for (boton in botones) {
            boton.setBackgroundResource(normalStyle)
        }
    }
    private fun highlightButton(button: Button, selectedStyle: Int) {
        button.setBackgroundResource(selectedStyle)
    }


}