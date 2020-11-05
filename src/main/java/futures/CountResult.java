
package futures;

/**
 * Le résultat du calcul de ByteCounter
 **/
public class CountResult {
        // Le nombre d'octes dans le résultat
	public final int byteCount;
        // Combien de temps ça a pris pour calculer le résultat
	public final long timeTaken;
	public CountResult(int b, long t) {
		byteCount  = b;
		timeTaken = t;
	}
}
