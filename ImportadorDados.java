package Trabalho1;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class ImportadorDados {

	public String importarDados(File f) throws IOException {
		Scanner sc = new Scanner(f, "UTF-8");
		String str = "";

		while (sc.hasNext()) {
			str += sc.nextLine();
		}

		sc.close();
		return str;
	}
}
