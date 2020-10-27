package com.example.castillotarea;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class VentanaDerecha {

    //sombreado de vertices de la forma
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    //COLOCA LOS SHADERS para ver las divisiones de figuras
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer; //almacena el orden de los vertices, como se van a dibujar


    static final int COORDS_PER_VERTEX = 3; // Numero de dimensiones a dibujar z,x,y
    static float squareCoords[] = {  //Sentido horario al contrario (antigorario)

            //Ventana Derecha
            //X, Y, Z
            0.7f, 0.3f, 0.0f,  // 1
            0.7f, 0.1f, 0.0f,  // 2
            0.55f, 0.1f, 0.0f,  // 3
            0.55f, 0.3f, 0.0f,  // 4



    };

    float color[] = {0.0f, 0.0f, 0.0f, 0.0f};
    private final int mProgram; // ES EL LIENZO(color negro), coordenadas, atributos, sombreados, texturas

    //private short drawOrder[] = {0, 1, 2, 3, 4, 5, 6, 7, 8 };

    public VentanaDerecha() {

        ByteBuffer bb = ByteBuffer.allocateDirect(
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);


    /*
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

     */

        //Carga del sombrado en la generacion del dibujo
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // creamos un programa vacio OpenGL ES
        mProgram = GLES20.glCreateProgram();

        // añadimos el sombreado de vertices al programa
        GLES20.glAttachShader(mProgram, vertexShader);

        // añadimos el sombreado de los fragmentos al programa
        GLES20.glAttachShader(mProgram, fragmentShader);

        // Creamos el programa ejecutable de OpenGL ES
        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX; //contador de vertices
    //Espacio de la matriz para los vertices
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes por vertice


    // Funcion para dibujar el triangulo
    public void draw() {
        // añadimos el programa al entorno de opengl
        GLES20.glUseProgram(mProgram);

        // obtenemos el identificador de los sombreados del los vertices a vPosition
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // habilitamos el manejo de los vertices del triangulo
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Preparamos los datos de las coordenadas del triangulo
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // Obtenemos el identificador del color del sombreado de los fragmentos
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Establecemos el color para el dibujo del triangulo
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // SE dibuja el triangulo
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount);

        // Deshabilitamos el arreglo de los vertices (que dibuje una sola vez)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}