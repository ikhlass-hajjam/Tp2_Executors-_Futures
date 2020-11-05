package futures;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class ByteCounter implements Callable<CountResult> {

	private final String currentUrl;

	public ByteCounter(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	/**
	 * Lit le contenu d'une URL, calcule le nombre d'octets et le temps écoulé
	 * @return CountResult, contient le nombre d'octets et le temps écoulé
	 * @throws Exception 
	 */
        @Override
	public CountResult call() throws Exception {
		long start = System.nanoTime(); // Nanosecondes
		int result = 0;
		URL url = new URL(currentUrl);
		// On ouvre l'URL en lecture
		LineNumberReader in =
			new LineNumberReader(
			new InputStreamReader(url.openStream()));
		// On lit le contenu de l'URL ligne par ligne
		try {
			String line;
			while ((line = in.readLine()) != null) {
				result += line.length();
			}
		} finally {
			in.close();
		}

		System.out.printf("L'URL %s compte %d octets %n", currentUrl, result);
		long timeTaken = System.nanoTime() - start; // nanosecondes
		// On renvoie le résultat du calcul
		return new CountResult(result, timeTaken  / 1000000 /* millisecondes */ );
	}
}
