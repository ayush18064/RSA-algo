import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

class RSA {

    // Calculate the Euler's Totient function
    int TotientFunc(int p, int q) {
        return (p - 1) * (q - 1);
    }

    // Compute the greatest common divisor using Euclidean algorithm
    int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Generate a random integer e such that 1 < e < TotientResult and gcd(e, TotientResult) = 1
    int RandomE(int TotientResult) {
        if (TotientResult <= 1) {
            throw new IllegalArgumentException("TotientResult must be greater than 1.");
        }

        Random rand = new Random();
        int randomNo;

        // Generate random number between 1 and TotientResult - 1
        do {
            randomNo = rand.nextInt(TotientResult - 1) + 1;
        } while (randomNo <= 0 || randomNo >= TotientResult);

        return randomNo;
    }
}

public class Main {
    public static void main(String[] args) {
        int p, q, e = 0;
        BigInteger n, d;
        BigInteger Num, c;

        RSA rsa = new RSA();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter 2 prime numbers: ");
        System.out.print("Enter the value of p: ");
        p = sc.nextInt();
        System.out.print("Enter the value of q: ");
        q = sc.nextInt();

        n = BigInteger.valueOf(p).multiply(BigInteger.valueOf(q));
        int TotientResult = rsa.TotientFunc(p, q);

        if (TotientResult > 1) {
            int Random_e = rsa.RandomE(TotientResult);
            if (rsa.gcd(Random_e, TotientResult) == 1) {
                e = Random_e;
            } else {
                System.out.println("Generated e is not coprime with TotientResult.");
            }
        } else {
            System.out.println("TotientResult is too small to find a suitable e.");
        }

        System.out.println("Value of e: " + e);

        if (e != 0) {
            BigInteger eBig = BigInteger.valueOf(e);
            BigInteger totientBig = BigInteger.valueOf(TotientResult);
            d = eBig.modInverse(totientBig);
            System.out.println("D: " + d);
        } else {
            System.out.println("Cannot compute D as e is 0 or invalid.");
        }

        // Encrypt a number
        System.out.println("Enter the number you want to encrypt:");
        Num = sc.nextBigInteger();
        BigInteger eBig = BigInteger.valueOf(e);
        c = Num.modPow(eBig, n);
        System.out.println("Cipher Text: " + c);

        sc.close(); // Close the scanner
    }
}
