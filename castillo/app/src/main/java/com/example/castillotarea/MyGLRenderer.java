package com.example.castillotarea;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    //locacion del triangulo
    private CuadradoCuerpo mCuadrado;
    private TorreIzquierda mTorreIzquierda;
    private TorreDerecha mTorreDerecha;
    private VentanaIzquierda mVentanaIzquierda;
    private VentanaDerecha mVentanaDerecha;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Se asigna el color del fondo de la palicacion
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //inicializando el triangulo
        mCuadrado = new CuadradoCuerpo();
        mTorreIzquierda = new TorreIzquierda();
        mTorreDerecha = new TorreDerecha();
        mVentanaIzquierda = new VentanaIzquierda();
        mVentanaDerecha = new VentanaDerecha();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        //dibujar el color del fondo
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //Dibujamos el triangulo
        mCuadrado.draw();
        mTorreIzquierda.draw();
        mTorreDerecha.draw();
        mVentanaIzquierda.draw();
        mVentanaDerecha.draw();

    }

    //funcion para cargar el sombreado de una figura
    public static int loadShader(int type, String shaderCode){

        // creamos el tipo de sombreado de los vertices (GLES20.GL_VERTEX_SHADER)
        // o los tipos de sombreado de los fragmentos (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // Añadimos el codigo fuente a los sombreados y lo compilamos
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        //La funcion regresara el valor de los sombreados generados
        return shader;
    }
}
