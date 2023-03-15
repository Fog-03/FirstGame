package es.fog03.tutorialligdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class FirstGame extends ApplicationAdapter {
	private OrthographicCamera camera; //Creación de la camara más tarde se definirá por completo
	private SpriteBatch batch;//Es una clase utilizada para crear figuras en 2D
	private Texture dropImg;//Crea una imagen que definiremos más tarde
	private Texture texture1;//Se crea esta textura para luego hacer la region texture
	private Texture baldeImg;
	private TextureRegion cestaImg;//Se crea una región de textura
	private Texture texture2;
	private TextureRegion cloudsImg1;
	private TextureRegion cloudsImg2;
	private TextureRegion cloudsImg3;
	private Sound dropSound;//Crea el sonido que se utilizará para la gota de agua
	private Music rainMusic;//Crea la música que se utilizará en el juego
	private Rectangle bucket;//Se crea el rectángulo de la cubeta
	private Rectangle cesta;//Se crea el rectángulo de la cesta
	
	@Override
	public void create () {
		batch = new SpriteBatch();//Se crea el Sprite Batch
		
		dropImg = new Texture("droplet.png");//Se crea la imagen de la gota
		baldeImg = new Texture("balde.png");//Se crea la imagen del balde
		texture1 = new Texture("cestas.png");//crea la textura para usar la región
		cestaImg = new TextureRegion(texture1, 86, 0, 81, 70);//(textura, x, y, width, heigth)en este caso ponen el eje de coordenadas arriba a la izquierda
		texture2 = new Texture("clouds.png");
		cloudsImg1 = new TextureRegion(texture2, 315, 85, 210, 90);
		cloudsImg2 = new TextureRegion(texture2, 640, 777, 235, 65);
		cloudsImg3 = new TextureRegion(texture2, 175, 777, 150, 60);
		
		camera = new OrthographicCamera();//Se crea la camara (rango de espacio en el que se puede interacturar)
		camera.setToOrtho(false, 800, 480);//Se le da los valores de tamaño que uno desee para que pueda interactuar el jugador
		
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));//Se declara el sonido de la gota
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));//Se declara la música de la lluvia
		//Empieza el sonido de fondo de forma inmediata
		rainMusic.setLooping(true);
		rainMusic.play();
		//Inicializamos los valores que le vamos a dar al cubo
		bucket = new Rectangle();
		bucket.y = 20; //Ponemos el cubo 20 pixeles por encima del borde inferior
		bucket.x = 800/2-64/2; //Queremos que el cubo comience centrado
		bucket.height = 64; //La altura sera 64 pixeles
		bucket.width = 64; //El ancho será 64 pixeles
		//Inicializamos los valores que le vamos a dar a la cesta
		cesta = new Rectangle();
		cesta.y = 20;
		cesta.height = cestaImg.getRegionHeight();
		cesta.width = cestaImg.getRegionWidth();
		cesta.x = 800/2-cestaImg.getRegionX();
		//Inicializamos los valores de cada nube
		
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);//Limpiamos el fondo y lo ponemos de color azul (r, g, b, a)
		camera.update();//Actualiza la cámara por si surge alguna interacción o se mueve algún objeto
		batch.setProjectionMatrix(camera.combined);//Dice el spriteBatch a usar el sistema de coordenadas para usar con la camara con una matriz de proyeccion
		batch.begin();
		batch.draw(dropImg, 30, 350, 30, 30);
		batch.draw(cloudsImg1, 0, 390);
		batch.draw(cloudsImg2, 100, 400);
		batch.draw(cloudsImg1, 200, 390);
		batch.draw(cloudsImg3, 350, 400);
		batch.draw(cloudsImg3, 450, 400);
		batch.draw(cloudsImg3, 520, 400);
		batch.draw(cloudsImg2, 600, 400);
		batch.draw(baldeImg, bucket.x, bucket.y);//Posición en la que comienza el objeto
		//batch.draw(cestaImg, cesta.x, cesta.y, 104, 90); //###Se puede remodelar el ancho y la altura para así escalar el objeto al tamaño que quieras
		batch.end();
		
		if(Gdx.input.isTouched()) {//Pregunta si la pantalla está siendo tocada por el mouse
	      Vector3 touchPos = new Vector3();//Crea un vector con tres componentes
	      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);//Pone como componente x del vector la posición x donde da el raton
	      camera.unproject(touchPos);//Esto incluso se puede evitar
	      bucket.x = touchPos.x - 64 / 2;//El valor de x del cubo pasa a ser la parte x donde tocas por el raton
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 300 * Gdx.graphics.getDeltaTime();
	}
		
	@Override
	public void dispose () {
		batch.dispose();
		dropImg.dispose();
	}
}
