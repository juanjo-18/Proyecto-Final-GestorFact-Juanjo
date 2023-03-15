package clases

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.TextPaint
import android.widget.Toast
import com.example.proyectofinal.R
import java.io.File
import java.io.FileOutputStream

class CrearPDF {

    //Datos de nuestra empresa
    var nombreEmpresaText = "Juanjo Medina Díaz"
    var calleEmpresaText = "Manolito, 14"
    var direccionEmpresaText = "29580, Cártama, Málaga"
    var dniEmpresaText = "72746587R"
    var correoEmpresaText = "juanjomedinadiaz@gmail.com"
    var telefonoEmpresaText = "654878290"

    //Tipo de factura y numero
    var tipoFacturaTexto = "Factura"
    var numeroFacturaTexto = "F-300/2023"

    //Datos de factura ordinaria
    var encabezadoTexto = "DATOS DE FACTURA ORDINARIA"
    var numeroPedidoTexto = "FAC0000001502"
    var numeroDePedidoTexto = "Número De Pedido: "
    var fechaEncabezadoTexto = "Fecha: "
    var fechaTexto = "10/03/2023"
    var tipoDePagoTexto = "Transferencia bancaria"
    var numeroDeCuentaTexto = "ES15 0049 4762 22 2016078965"

    //Datos del cliente
    var encabezadoDatosClienteTexto = "DATOS DEL CLIENTE"
    var nombreClienteTexto = "Amazon S.L"
    var dniClienteTexto = "B13071267"
    var calleClienteTexto = "POLIGONO INDUSTRIAL EL OLIVAR, PARCELA 02.02"
    var direccionClienteTexto = "11160, BARBATE, CÁDIZ, España"

    //Datos final de la factura
    var baseTexto = "BASE"
    var baseEnEurosTexto = "200€"
    var ivaTexto = "IVA 21,00%"
    var ivaNumeroTexto = "28,52"
    var totalTexto = "TOTAL"
    var totalNumeroTexto = "250€"


    fun generarPdf(resources: Resources, context: Context) {

        var pdfDocument = PdfDocument()
        var paint = Paint()
        var descripcion = TextPaint()

        var paginaInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        //Logo
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 175, 115, false)
        canvas.drawBitmap(bitmapEscala, 40f, 40f, paint)

        //Linea vertical al lado del logo y al lado del texto
        paint.strokeWidth = 2f
        paint.color = Color.BLUE
        canvas.drawLine(300f, 150f, 300f, 40f, paint)
        canvas.drawLine(550f, 150f, 550f, 40f, paint)

        //Derecha del logo (Datos de mi empresa)
        var nombreEmpresa = TextPaint()
        nombreEmpresa.textSize = 15f
        var calleEmpresa = TextPaint()
        calleEmpresa.textSize = 15f
        var direccionEmpresa = TextPaint()
        direccionEmpresa.textSize = 15f
        var dniEmpresa = TextPaint()
        dniEmpresa.textSize = 15f
        var correoEmpresa = TextPaint()
        correoEmpresa.textSize = 15f
        var telefonoEmpresa = TextPaint()
        telefonoEmpresa.textSize = 15f

        canvas.drawText(nombreEmpresaText, 315f, 60f, nombreEmpresa)
        canvas.drawText(calleEmpresaText, 315f, 80f, calleEmpresa)
        canvas.drawText(direccionEmpresaText, 315f, 100f, direccionEmpresa)
        canvas.drawText(dniEmpresaText, 315f, 120f, dniEmpresa)
        canvas.drawText(correoEmpresaText, 565f, 60f, correoEmpresa)
        canvas.drawText(telefonoEmpresaText, 565f, 80f, telefonoEmpresa)


        //Añadir el titulo y numero de factura centrado
        var tipoFactura = TextPaint()
        var numeroFactura = TextPaint()
        tipoFactura.textSize = 22f
        numeroFactura.textSize = 20f
        tipoFactura.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        val canvasWidth = canvas.width.toFloat()
        val longitudTipoFactura = paint.measureText(tipoFacturaTexto)
        val xTipoFactura = (canvasWidth - longitudTipoFactura) / 2f
        canvas.drawText(tipoFacturaTexto, xTipoFactura - 20f, 190f, tipoFactura)
        val longitudNumeroFactura = paint.measureText(numeroFacturaTexto)
        val xNumeroFactura = (canvasWidth - longitudNumeroFactura) / 2f
        canvas.drawText(numeroFacturaTexto, xNumeroFactura - 20f, 215f, numeroFactura)


        //Datos de factura ordinaria + linea horizontal
        var encabezado = TextPaint()
        encabezado.textSize = 16f
        encabezado.color = Color.BLUE
        encabezado.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(encabezadoTexto, 45f, 285f, encabezado)

        //Linea gris
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawLine(45f, 295f, 370f, 295f, paint)

        var numeroDePedido = TextPaint()
        numeroDePedido.textSize = 15f
        canvas.drawText(numeroDePedidoTexto, 45f, 315f, numeroDePedido)
        var numeroPedido = TextPaint()
        numeroPedido.textSize = 15f
        numeroPedido.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(numeroPedidoTexto, 180f, 315f, numeroPedido)

        var fechaEncabezado = TextPaint()
        fechaEncabezado.textSize = 15f
        canvas.drawText(fechaEncabezadoTexto, 45f, 335f, fechaEncabezado)
        var fecha = TextPaint()
        fecha.textSize = 15f
        fecha.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(fechaTexto, 92f, 335f, fecha)

        var tipoDePago = TextPaint()
        tipoDePago.textSize = 15f
        tipoDePago.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(tipoDePagoTexto, 45f, 355f, tipoDePago)

        var numeroDeCuenta = TextPaint()
        numeroDeCuenta.textSize = 15f
        numeroDeCuenta.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(numeroDeCuentaTexto, 45f, 375f, numeroDeCuenta)

        //Linea vertical al lado de datos de factura
        paint.strokeWidth = 2f
        val xLinea = (canvasWidth - 2f) / 2f
        paint.color = Color.BLUE
        canvas.drawLine(xLinea, 400f, xLinea, 270f, paint)


        //Datos del cliente
        var encabezadoDatosClietne = TextPaint()
        encabezadoDatosClietne.textSize = 16f
        encabezadoDatosClietne.color = Color.BLUE
        encabezadoDatosClietne.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(encabezadoDatosClienteTexto, 430f, 285f, encabezadoDatosClietne)

        //Linea gris horizontal
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawLine(430f, 295f, 735f, 295f, paint)

        var nombreClietne = TextPaint()
        nombreClietne.textSize = 15f
        nombreClietne.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(nombreClienteTexto, 430f, 315f, nombreClietne)
        var dniCliente = TextPaint()
        dniCliente.textSize = 15f
        canvas.drawText(dniClienteTexto, 430f, 335f, dniCliente)
        var calleCliente = TextPaint()
        calleCliente.textSize = 15f
        canvas.drawText(calleClienteTexto, 430f, 355f, calleCliente)
        var direccionCliente = TextPaint()
        direccionCliente.textSize = 15f
        canvas.drawText(direccionClienteTexto, 430f, 375f, direccionCliente)


        //Tabla de productos (Actual maximo de productos de 18)
        val columnas = listOf("Nombre", "Precio", "Unidades", "Subtotal", "IMP.")
        val datos = listOf(
            listOf("Martillo", "10€", "15", "150€", "IVA 21,00%"),
            listOf("Sierra", "12€", "5", "60€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%"),
            listOf("Martillo", "10€", "15", "150€", "IVA 21,00%"),
            listOf("Sierra", "12€", "5", "60€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%"),
            listOf("Martillo", "10€", "15", "150€", "IVA 21,00%"),
            listOf("Sierra", "12€", "5", "60€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%"),
            listOf("Martillo", "10€", "15", "150€", "IVA 21,00%"),
            listOf("Sierra", "12€", "5", "60€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%"),
            listOf("Martillo", "10€", "15", "150€", "IVA 21,00%"),
            listOf("Sierra", "12€", "5", "60€", "IVA 21,00%"),
            listOf("Destornillador", "20€", "10", "200€", "IVA 21,00%")

        )
        crearTabla(pagina1, columnas, datos, 470f)


        //Datos finales de la factura
        var base = TextPaint()
        base.textSize = 16f
        base.color = Color.BLUE
        base.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(baseTexto, 550f, 900f, base)
        var baseEuro = TextPaint()
        baseEuro.textSize = 16f
        baseEuro.color = Color.BLUE
        baseEuro.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(baseEnEurosTexto, 740f, 900f, baseEuro)

        //Linea gris horizontal
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawLine(550f, 910f, 740f + paint.measureText(baseEnEurosTexto) + 7f, 910f, paint)

        baseEuro.color = Color.BLACK
        canvas.drawText(baseEnEurosTexto, 550f, 930f, baseEuro)
        var iva = TextPaint()
        iva.textSize = 16f
        canvas.drawText(ivaTexto, 645f, 930f, iva)
        var ivaNumero = TextPaint()
        ivaNumero.textSize = 16f
        canvas.drawText(ivaNumeroTexto, 740f, 930f, ivaNumero)

        //Linea gris horizontal
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawLine(550f, 940f, 740f + paint.measureText(baseEnEurosTexto) + 7f, 940f, paint)

        var total = TextPaint()
        total.textSize = 18f
        total.color = Color.BLUE
        total.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(totalTexto, 550f, 960f, total)
        var totalNumero = TextPaint()
        totalNumero.textSize = 18f
        totalNumero.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        canvas.drawText(totalNumeroTexto, 740f, 960f, totalNumero)


        //Lineas azules finales del documento
        paint.strokeWidth = 4f
        paint.color = Color.BLUE
        canvas.drawLine(10f, 1000f, canvasWidth - 10f, 1000f, paint)
        canvas.drawLine(10f, 1020f, canvasWidth - 10f, 1020f, paint)











        pdfDocument.finishPage(pagina1)

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "ArchivoPersonalizado.pdf"
        )
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "Se creo el PDF correctamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        pdfDocument.close()

    }

    fun crearTabla(
        page: PdfDocument.Page,
        columnas: List<String>,
        datos: List<List<String>>,
        startY: Float
    ) {
        val paint = Paint()
        val titulo = TextPaint()
        titulo.textSize = 15f
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))

        // Calcular la posición de los títulos de columna
        val columnWidth = (page.canvas.width - 100) / columnas.size
        var currentX = 50f
        for (tituloColumna in columnas) {
            val textWidth = titulo.measureText(tituloColumna)
            page.canvas.drawText(
                tituloColumna,
                currentX + (columnWidth - textWidth) / 2,
                startY + 20f,
                titulo
            )
            currentX += columnWidth
        }

        // Calcular la posición de los datos de la tabla
        val dato = TextPaint()
        dato.textSize = 12f
        currentX = 50f
        var currentY = startY + 50f
        for (fila in datos) {
            for (columna in fila) {
                val textWidth = dato.measureText(columna)
                page.canvas.drawText(
                    columna,
                    currentX + (columnWidth - textWidth) / 2,
                    currentY,
                    dato
                )
                currentX += columnWidth
            }
            currentY += 20f
            currentX = 50f
        }

        // Dibujar las líneas de la tabla
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        currentX = 50f
        currentY = startY + 50f
        val tableHeight = currentY - 30 + (datos.size + 1) * 20f
        page.canvas.drawRect(
            40f,
            startY,
            page.canvas.width - 40f,
            tableHeight,
            paint
        )//Primera linea horizontal del marco superior
        page.canvas.drawLine(
            currentX - 10f,
            currentY - 20,
            page.canvas.width - 40f,
            currentY - 20,
            paint
        )//Linea horizontal debajo de cabezera
        page.canvas.drawLine(
            currentX,
            tableHeight,
            page.canvas.width - 50f,
            tableHeight,
            paint
        ) // Dibujar línea inferior
        for (i in 0 until columnas.size - 1) {
            currentX += columnWidth
            page.canvas.drawLine(
                currentX,
                currentY - 50f,
                currentX,
                tableHeight,
                paint
            ) //Lineas verticales entre columnas
        }

        paint.style = Paint.Style.FILL
        currentX = 50f
        currentY += 20f
        for (i in 1..datos.size - 1) {
            // Dibujar una línea horizontal después de cada fila de datos
            page.canvas.drawLine(
                40f,
                currentY - 16f,
                page.canvas.width - 40f,
                currentY - 16f,
                paint
            )
            currentX = 50f
            currentY += 20f
        }
    }
}