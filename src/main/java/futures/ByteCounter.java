package futures;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ByteCounter implements Callable<CountResult> {

    private final String currentUrl;
    private final CountDownLatch compteARebours;

    public ByteCounter(String currentUrl, CountDownLatch compteARebours) {
        this.currentUrl = currentUrl;
        this.compteARebours = compteARebours;
    }

    /**
     * Lit le contenu d'une URL, calcule le nombre d'octets et le temps écoulé
     * Décrémente le compte à rebours
     * @return CountResult, contient le nombre d'octets et le temps écoulé
     * @throws IOException en cas d'erreur de lecture
     */
    @Override
    public CountResult call() throws IOException {
        long startTime = System.nanoTime(); // Nanosecondes
        int numberOfBytes = 0;
        URL url = new URL(currentUrl);

        // On lit le contenu de l'URL ligne par ligne
        try (LineNumberReader in =
                 new LineNumberReader(
                     new InputStreamReader(url.openStream())
                 )
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                numberOfBytes += line.length();
            }
        }

        System.out.printf("L'URL %s compte %d octets %n", currentUrl, numberOfBytes);
        long timeTaken = System.nanoTime() - startTime; // nanosecondes
        // On décompte un résultat
        compteARebours.countDown();
        // On renvoie le résultat du calcul
        return new CountResult(numberOfBytes, timeTaken / 1000000 /* millisecondes */);
    }
}
