package fes.aragon.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fondo extends ComponentesJuego {
	private int yy = 0;
	private int xx = 0;
	private Random rd = new Random();
	private int rdx = rd.nextInt(10 + 0) + 0;
	private int rdy = rd.nextInt(10 + 0) + 0;
	private Image arribaImg;
	private Image abajoImg;
	private Image derechaImg;
	private Image izquierdaImg;
	private Image imagen;
	private Image moneda;
	private Stage ventana;
	private ArrayList<String> comandos = new ArrayList<>();
	private int ancho = 40;
	private int alto = 40;
	private boolean iniciar = false;
	private GraphicsContext graficos;
	private int indice = 0;
	private int moverCuadros = 0;
	private int repetir = 0;
	private String comando = "";
	private boolean termina=false;
	private boolean arriba=false;
	private boolean abajo=false;
	private boolean derecha=false;
	private boolean izquierda=false;

	public Fondo(int x, int y, String imagen, int velocidad, Stage ventana) {
		super(x, y, imagen, velocidad);
		this.derechaImg=new Image(imagen);
		this.izquierdaImg=new Image("/fes/aragon/recursos/izquierda.png");
		this.arribaImg=new Image("/fes/aragon/recursos/arriba.png");
		this.abajoImg=new Image("/fes/aragon/recursos/abajo.png");
		this.imagen = derechaImg;
		this.moneda= new Image("/fes/aragon/recursos/moneda.png");
		this.ventana = ventana;
	}

	@Override
	public void pintar(GraphicsContext graficos) {
		// TODO Auto-generated method stub
		this.graficos = graficos;
		int xx = 50;
		int yy = 50;
		for (int j = 1; j <= 10; j++) {
			for (int i = 1; i <= 10; i++) {
				graficos.strokeRect(xx, yy, 50, 50);
				xx += 50;
			}
			xx = 50;
			yy += 50;
		}		
		graficos.drawImage(imagen, x, y, ancho, alto);
		graficos.strokeRect(x, y, ancho, alto);
		if (!comandos.isEmpty()) {
			if(indice<comandos.size()) {
				graficos.strokeText(comandos.get(indice), 100, 40);
			}
		}
		graficos.drawImage(moneda, (55+(ancho+10)*rdx), (55+(ancho+10)*rdy), 40, 40);
	}

	@Override
	public void teclado(KeyEvent evento, boolean presiona) {
		// TODO Auto-generated method stub
		if (presiona) {
			switch (evento.getCode().toString()) {
			case "A":
				try {
					this.abrirArchivo();
					graficos.clearRect(0, 0, 600, 600);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "R":
				iniciar();
				this.ejecutar();
				this.iniciar = true;
				graficos.clearRect(0, 0, 600, 600);
				break;
			}
		}
	}

	@Override
	public void raton(KeyEvent evento) {

	}

	@Override
	public void logicaCalculos() {
		if (iniciar) {
			switch (this.comando) {
			case "arriba":
			case "abajo":
			case "izquierda":
			case "derecha":
				indice++;
				this.ejecutar();
				break;
			case "coloca":			
				if (x < xx) {
					x += velocidad;
					this.imagen=this.derechaImg;
					graficos.clearRect(0, 0, 600, 600);
				} else {
					if (y < yy) {
						this.imagen=this.abajoImg;
						y += velocidad;
						graficos.clearRect(0, 0, 600, 600);
					}
				}				
				if ((x >= xx) && (y >= yy)) {
					indice++;
					this.ejecutar();
				}
				break;
				
			case "mover":
				if(arriba) {
					if (y > yy && y>55) {
						y -= velocidad;
						graficos.clearRect(0, 0, 600, 600);
					}else {
						indice++;
						this.ejecutar();
					}
				}
				if(abajo) {
					if (y < yy && y<505) {
						y += velocidad;
						graficos.clearRect(0, 0, 600, 600);
					}else {
						indice++;
						this.ejecutar();
					}
				}
				if(izquierda) {
					if (x > xx && x>55) {
						x -= velocidad;
						graficos.clearRect(0, 0, 600, 600);
					}else {
						indice++;
						this.ejecutar();
					}
				}
				if(derecha) {
					if (x < xx && x<505) {
						x += velocidad;
						graficos.clearRect(0, 0, 600, 600);
					}else {
						indice++;
						this.ejecutar();
					}
				}
				break;
			case "ver":
				System.out.println("entro1");
				break;
			}
		}
	}

	private void ejecutar() {
		if (indice < comandos.size()) {
			String string = comandos.get(indice);
			String[] datos = string.split(" ");
			System.out.println(datos[0]);
			switch (datos[0]) {
			case "arriba":
				this.arriba = true;
				this.abajo = false;
				this.izquierda = false;
				this.derecha = false;
				this.imagen=this.arribaImg;
				this.comando="arriba";
				break;
			case "abajo":
				this.arriba = false;
				this.abajo = true;
				this.izquierda = false;
				this.derecha = false;
				this.imagen=this.abajoImg;
				this.comando="abajo";
				break;
			case "izquierda":
				this.arriba = false;
				this.abajo = false;
				this.izquierda = true;
				this.derecha = false;
				this.imagen=this.izquierdaImg;
				this.comando="izquierda";
				break;
			case "derecha":
				this.arriba = false;
				this.abajo = false;
				this.izquierda = false;
				this.derecha = true;
				this.imagen=this.derechaImg;
				this.comando="derecha";
				break;
			case "coloca":
				x = 55;
				y = 55;
				xx = Integer.parseInt(datos[1]);
				yy = Integer.parseInt(datos[2]);
				xx = (x + (ancho + 10) * (xx - 1));
				yy = (y + (alto + 10) * (yy - 1));
				this.comando = "coloca";
				break;
			case "mover":				
				moverCuadros = Integer.parseInt(datos[1]);
				if (arriba) {
					yy = (y - (alto + 10) * moverCuadros);
				}
				if(abajo) {
					yy = (y + (alto + 10) * moverCuadros);
				}
				if(izquierda) {
					xx = (x - (ancho + 10) * moverCuadros);
				}
				if(derecha) {
					xx = (x + (ancho + 10) * moverCuadros);
				}
				this.comando = "mover";
				break;
			case "ver":
				System.out.println("entro2"); 
			default:
				break;
			}

		} else {
			System.out.println("limite");
			this.iniciar = false;
			this.indice = 1;
		}

	}

	private void abrirArchivo() throws IOException, ClassNotFoundException {
		FileChooser archivos = new FileChooser();
		archivos.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos compilador", "*.fes"));
		archivos.setTitle("Abrir archivo de Compilador");
		archivos.setInitialDirectory(new File(System.getProperty("user.dir")));
		File ruta = archivos.showOpenDialog(this.ventana);
		if (ruta != null) {
			FileReader fr = new FileReader(ruta);
			BufferedReader buff = new BufferedReader(fr);
			String cad;
			this.comandos.clear();
			this.iniciar();
			while ((cad = buff.readLine()) != null) {
				comandos.add(cad);
			}		
			buff.close();
			fr.close();
		}

	}
	private void iniciar() {
		x=55;
		y=55;
		xx=0;
		yy=0;
		indice=0;
		this.imagen=this.derechaImg;
		moverCuadros = 0;
		comando = "";
		arriba=false;
		abajo=false;
		derecha=false;
		izquierda=false;
	}
}
