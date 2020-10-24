package ar.com.ada.api.mutant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.security.Crypto;
import ar.com.ada.api.mutant.services.MutantService;

@SpringBootTest
class MutantApplicationTests {

	@Autowired
	MutantService mutantService;

	@Test
	void contextLoads() {
	}

	@Test
	void ADN_isValid() {

	}

	@Test
	void ADN_isMutant() {
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Mutants ");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant));

		String[] dnaMutant2 = { "AAAAAA", "CAGTAC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Mutants 2 secuences diagonal");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant2));

	}

	@Test
	void ADN_isHuman() {
		String[] dnaMutant = { "ATGCGA", "CTGTGC", "TTATGT", "AGAATG", "CCCTTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Human");
		System.out.println("************");

		assertFalse(mutantService.isMutant(dnaMutant));
	}

	@Test
	void MatrixTest() {
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCTTA", "TCACTG" };
		DNASample sample = new DNASample(dnaMutant);
		char[][] matrix = sample.toMatrix();
		char[][] invertedMatrix = sample.toMatrixInverted();
		String[] dnaInverted = new String[dnaMutant.length];

		for (int i = 0; i < invertedMatrix.length; i++) {
			StringBuilder sb = new StringBuilder();

			for (char caracter : invertedMatrix[i]) {

				sb.append(caracter);
			}
			dnaInverted[i] = sb.toString();
		}
		DNASample sampleInverted = new DNASample(dnaInverted);
		char[][] restoredMatrix = sampleInverted.toMatrixInverted();
		boolean iguales = matrix.length > 0;
		for (int i = 0; i < matrix.length; i++) {

			for (int j = 0; j < matrix.length; j++) {
				iguales = iguales && matrix[i][j] == restoredMatrix[i][j];
			}
		}
		System.out.println("************");
		System.out.println("Testing Matrix");
		System.out.println("************");

		assertTrue(iguales, "las matrices no son iguales :(");
	}

	@Test
	void EncryptionTest() {

		String textoClaro = "Este es un texto que todos pueden leer";

		// aca va algo que sepamos que cambie por cada usuario o transaccion
		String unSaltoLoco = "un numero random";

		// Aca vamos a dejar el texto encriptado(reversible!)
		String textoEncriptado = "";

		textoEncriptado = Crypto.encrypt(textoClaro, unSaltoLoco);

		// Este print no lo hagan en los testing reales! si bien sirve para buscar, lo
		// mejor es
		// tenerlos desactivados! En tal caso debuguean!
		System.out.println("el texto encriptado es: " + textoEncriptado);

		// Aca vamos a dejar el texto desencriptado de "textoEncryptado"
		String textoDesencriptado = "";

		// Desencripto!!
		textoDesencriptado = Crypto.decrypt(textoEncriptado, unSaltoLoco);

		// Todo va a estar bien, si el "textoClaro" es igual al "textoDesencriptado";
		assertTrue(textoClaro.equals(textoDesencriptado));

	}

	@Test
	void EncryptionPWSinSaltoSTest() {

		String pwClaraUsuario1 = "a123456";

		String unSaltoLocoUsuario1 = "-";

		String pwClaraUsuario2 = "a123456";

		String unSaltoLocoUsuario2 = "-";

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwEncriptada1 = "";

		pwEncriptada1 = Crypto.encrypt(pwClaraUsuario1, unSaltoLocoUsuario1);

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwEncriptada2 = "";

		pwEncriptada2 = Crypto.encrypt(pwClaraUsuario2, unSaltoLocoUsuario2);

		System.out.println("pw1 encriptada: " + pwEncriptada1);
		System.out.println("pw2 encriptada: " + pwEncriptada2);

		// Todo va a estar bien, si el "textoClaro" es igual al "textoDesencriptado";
		assertTrue(pwEncriptada1.equals(pwEncriptada2));

	}

	@Test
	void EncryptionPWConSaltoSTest() {

		String pwClaraUsuario1 = "a123456";

		String unSaltoLocoUsuario1 = "usuario1";

		String pwClaraUsuario2 = "a123456";

		String unSaltoLocoUsuario2 = "usuario2";

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwEncriptada1 = "";

		pwEncriptada1 = Crypto.encrypt(pwClaraUsuario1, unSaltoLocoUsuario1);

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwEncriptada2 = "";

		pwEncriptada2 = Crypto.encrypt(pwClaraUsuario2, unSaltoLocoUsuario2);

		System.out.println("pw1 encriptada: " + pwEncriptada1);
		System.out.println("pw2 encriptada: " + pwEncriptada2);

		// Todo va a estar bien, si el "textoClaro" es igual al "textoDesencriptado";
		assertFalse(pwEncriptada1.equals(pwEncriptada2));

	}

	@Test
	void HashPWSinSaltoSTest() {

		String pwClaraUsuario1 = "a123456";

		String unSaltoLocoUsuario1 = "-";

		String pwClaraUsuario2 = "a123456";

		String unSaltoLocoUsuario2 = "-";

		String pwHasheada1 = "";

		pwHasheada1 = Crypto.encrypt(pwClaraUsuario1, unSaltoLocoUsuario1);

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwHasheada2 = "";

		pwHasheada2 = Crypto.encrypt(pwClaraUsuario2, unSaltoLocoUsuario2);

		System.out.println("pw1 hasheada: " + pwHasheada1);
		System.out.println("pw2 hasheada: " + pwHasheada2);

		// Todo va a estar bien, si el "textoClaro" es igual al "textoDesencriptado";
		assertTrue(pwHasheada1.equals(pwHasheada2));

	}

	@Test
	void HashPWConSaltoSTest() {

		String pwClaraUsuario1 = "a123456";

		String unSaltoLocoUsuario1 = "usuario1";

		String pwClaraUsuario2 = "a123456";

		String unSaltoLocoUsuario2 = "usuario2";

		String pwHasheada1 = "";

		pwHasheada1 = Crypto.encrypt(pwClaraUsuario1, unSaltoLocoUsuario1);

		// Aca vamos a dejar el texto encriptado(reversible!)
		String pwHasheada2 = "";

		pwHasheada2 = Crypto.encrypt(pwClaraUsuario2, unSaltoLocoUsuario2);

		System.out.println("pw1 hasheada: " + pwHasheada1);
		System.out.println("pw2 hasheada: " + pwHasheada2);

		// Todo va a estar bien, si el "textoClaro" es igual al "textoDesencriptado";
		assertFalse(pwHasheada1.equals(pwHasheada2));

	}

	@Test
	void HashTest() {

		String textoClaro = "Este es un texto que todos pueden leer";

		// aca va algo que sepamos que cambie por cada usuario o transaccion
		String unSaltoLoco = "algo atado al usuario, ej UserId 20";

		// Aca vamos a dejar el texto hasheado(NO reversible)
		String textoHasheado = "";

		textoHasheado = Crypto.hash(textoClaro, unSaltoLoco);

		// Este print no lo hagan en los testing reales! si bien sirve para buscar, lo
		// mejor es
		// tenerlos desactivados! En tal caso debuguean!
		System.out.println("el texto hasheado es: " + textoHasheado);

		// Aca vamos a dejar el texto desencriptado de "textoEncryptado"
		String hashEsperado = "lxT/9Zj6PUyV/xTfCS90qfLMNEL7wnvg8VxsG/slFvZghZvQvFCZQvg584s6TMlkHqJ3wMA2J9rofsERmKGSUg==";

		// Todo va a estar bien, si el hash del texto es el
		assertTrue(textoHasheado.equals(hashEsperado));

	}

}
