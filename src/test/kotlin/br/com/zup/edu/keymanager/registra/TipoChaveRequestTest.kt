package br.com.zup.edu.keymanager.registra

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    @Nested
    inner class ChaveAleatoriaTest {

        @Test
        fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertTrue(tipoChave.valida(null))
            assertTrue(tipoChave.valida(""))
        }

        @Test
        fun `nao deve ser valido quando cahve aleatoria possuir um valor`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertFalse(tipoChave.valida("um valor qualquer"))
        }

    }

    @Nested
    inner class CpfTest {

        @Test
        fun `deve ser valido quando cpf for um numero valido`() {
            val tipoChave = TipoChaveRequest.CPF

            assertTrue(tipoChave.valida("002.821.160-01"))
            assertTrue(tipoChave.valida("00282116001"))
        }

        @Test
        fun `nao deve ser valido quando cpf for um numero invalido`() {
            val tipoChave = TipoChaveRequest.CPF

            assertFalse(tipoChave.valida("cpf invalido"))
        }

        @Test
        fun `deve ser valido quando cpf nao for informado`() {
            val tipoChave = TipoChaveRequest.CPF

            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }

    }

    @Nested
    inner class CelularTest {

        @Test
        fun `deve ser valido quando celular for um numero valido`() {
            val tipoChave = TipoChaveRequest.CELULAR

            assertTrue(tipoChave.valida("+5511987654321"))
        }

        @Test
        fun `nao deve ser valido quando celular for um numero invalido`() {
            val tipoChave = TipoChaveRequest.CELULAR

            assertFalse(tipoChave.valida("11987654321"))
            assertFalse(tipoChave.valida("+55a11987654321"))
        }

        @Test
        fun `nao deve ser valido quando celular nao for informado`() {
            val tipoChave = TipoChaveRequest.CELULAR

            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }

    }

    @Nested
    inner class EmailTest {

        @Test
        fun `deve ser valido quando email for endereco valido`() {
            val tipoChave = TipoChaveRequest.EMAIL

            assertTrue(tipoChave.valida("luiz@gmail.com"))
        }

        @Test
        fun `nao deve ser valido quando email estiver em um formato invalido`() {
            val tipoChave = TipoChaveRequest.EMAIL

            assertFalse(tipoChave.valida("luizgmail.com"))
            assertFalse(tipoChave.valida("luiz@gmail.com."))
        }

        @Test
        fun `nao deve ser valido quando email nao for informado`() {
            val tipoChave = TipoChaveRequest.EMAIL

            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }

    }

}