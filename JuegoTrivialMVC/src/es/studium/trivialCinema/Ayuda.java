package es.studium.trivialCinema;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Ayuda
{
	public Ayuda()
	{
		String ayuda = "Ayuda.chm";
		try
		{
			File archivoAyuda = new File(ayuda);
			Desktop.getDesktop().open(archivoAyuda);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}