package fes.aragon.compilador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Archivo {
	private File archivo;

	public Archivo(File archivo) {
		super();
		this.archivo = archivo;
	}

	public void escribir(String dato, boolean borrar) {
		FileWriter fw = null;
		try {
			if (borrar) {
				fw = new FileWriter(archivo.getAbsoluteFile(), true);
			} else {
				fw = new FileWriter(archivo.getAbsoluteFile(), false);
			}
			PrintWriter salida = new PrintWriter(fw);
			salida.println(dato);
			salida.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
