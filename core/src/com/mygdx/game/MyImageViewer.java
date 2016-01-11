package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class MyImageViewer extends ApplicationAdapter {

    private final String TAG = "MyImageViewer";
    private TextButton.TextButtonStyle textBStyle = new TextButton.TextButtonStyle();
    private BitmapFont bitmapFont;
    private Stage myStage ;
    private Texture buttonTexture;
    private TextButton textButton;
    private ImageButton imageButton;
    int Width,Height;

    SpriteBatch batch;
	Texture img;
	Sprite SpriteImage;
    OrthographicCamera camera;
    InputHandler InputProcessor;
    ArrayList<SelectionBox> BoxList = new ArrayList<SelectionBox>();
    ShapeRenderer WidgetRenderer;


    private CallbackInterface StartActivityCallback ;

    public interface CallbackInterface {
        public String StartImageBrowser();
    }

    public void setStartActivityCallback(CallbackInterface callback) {
        StartActivityCallback = callback;
        InputProcessor.setCallback(callback);
    }

    public MyImageViewer(CallbackInterface callbackInterface) {
        StartActivityCallback = callbackInterface;
    }

	@Override
	public void create () {

        Width = Gdx.graphics.getWidth(); Height = Gdx.graphics.getHeight();
        camera  = new OrthographicCamera(Width,Height);
        camera.position.set(camera.viewportWidth / 2f,
                camera.viewportHeight / 2f, 0);
        camera.zoom -= 0.5;
        camera.rotate(90);
        camera.update();
        InputProcessor = new InputHandler(camera,BoxList);
        InputProcessor.setCallback(StartActivityCallback);

		batch = new SpriteBatch();
        WidgetRenderer = new ShapeRenderer();
		img = new Texture("inscription.jpg");

        SpriteImage = new Sprite(img);
        SpriteImage.setScale(1);

        /*Button Initialization */
        bitmapFont = new BitmapFont();
        myStage = new Stage();

        textBStyle.font = bitmapFont;
        textBStyle.up = textBStyle.down = textBStyle.checked =
                new TextureRegionDrawable(new TextureRegion(new Texture("uni.png")));
        textButton = new TextButton("",textBStyle);
//        textButton.setOrigin(textButton.getWidth()/2,textButton.getHeight()/2);
//        textButton.addAction(Actions.rotateBy(180,0.2f));
        //textButton.getLabel().setRotation(90);
        textButton.setPosition(100, Height - 80);
//        textButton.scaleBy(40);

        ImageButton.ImageButtonStyle imStyle = new ImageButton.ImageButtonStyle();
        imStyle.up = imStyle.down = imStyle.checked =
                new TextureRegionDrawable(new TextureRegion(new Texture("browser.png")));
        imageButton = new ImageButton(imStyle);
        imageButton.setPosition(100, Height - 100);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StartActivityCallback.StartImageBrowser();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(-Width /2 + 150 , Height / 2 - 100);

        table.add(imageButton).width(100);
        table.add(textButton).width(200).height(200);
        myStage.addActor(table);
//        myStage.addActor(textButton);
//        myStage.addActor(imageButton);

        /*Setting up Input Processing */
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(myStage);
        multiplexer.addProcessor(new GestureDetector(new GestureProcessor(camera, BoxList)));
        multiplexer.addProcessor(InputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glLineWidth(8);

        batch.setProjectionMatrix(camera.combined);
        WidgetRenderer.setProjectionMatrix(camera.combined);
		batch.begin();
		SpriteImage.draw(batch);
		batch.end();
        WidgetRenderer.setColor(Color.YELLOW);
        for(SelectionBox s:BoxList) s.Draw(WidgetRenderer);
        myStage.draw();
	}
}
