import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import futures.*;

public class Main {
	public static final int HOWMANYTHREADS = 6;

    public static void main(String[] args) 
		throws InterruptedException, ExecutionException {
		// voir : http://www.c2.com/cgi/wiki?DoubleBraceInitialization
		// Les URL à traiter
		List<String> urls = new ArrayList<String>() {
			{
				add("http://www.java2jee.blogspot.com");
				add("http://www.univ-jfc.fr");
				add("https://www.irit.fr/");
				add("http://www.google.fr");
				add("http://apache.org/");
				add("https://sourceforge.net/");
			}
		};
		// La liste des résultats
		List<Future<CountResult>> futures = new ArrayList<>(urls.size());
		// On crée l'exécuteur
		final ExecutorService executor = Executors.newFixedThreadPool(HOWMANYTHREADS);

		try {
			long start = System.nanoTime();
			// On soumet tous les travaux à l'exécuteur
			for (String url : urls) {
                                Callable<CountResult> callable = new ByteCounter(url);
                                Future<CountResult> future = executor.submit(callable);
				futures.add(future);
			}

			// On parcourt les résultats pour calculer le temps d'exécution
			long byteResult = 0; long totalIndividualTimes = 0;
			for (Future<CountResult> future : futures) {
				CountResult result = future.get(); // Bloquant
				byteResult += result.byteCount;
				totalIndividualTimes += result.timeTaken;
			}
			System.out.println("\nNombre total d'octets : " + byteResult);
			System.out.println("Temps total de calcul : " + (System.nanoTime() - start) / 1000000 + "ms");
			System.out.println("Somme des temps individuels: " + totalIndividualTimes + "ms");
		} finally {
			executor.shutdown();
		}
	}
}
