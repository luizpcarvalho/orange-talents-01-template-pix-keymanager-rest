package br.com.zup.edu.keymanager.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    private val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando statusException for not found`() {
        // cenário
        val mensagem = "nao encontrado"
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        // ação
        val resposta = GlobalExceptionHandler().handle(requestGenerica, notFoundException)

        // validação
        with(resposta) {
            assertEquals(HttpStatus.NOT_FOUND, this.status)
            assertNotNull(this.body())
            assertEquals(mensagem, (this.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 422 quando statusException for already exists`() {
        // cenário
        val mensagem = "chave ja existente"
        val alreadyExistsException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        // ação
        val resposta = GlobalExceptionHandler().handle(requestGenerica, alreadyExistsException)

        // validação
        with(resposta) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, this.status)
            assertNotNull(this.body())
            assertEquals(mensagem, (this.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 400 quando statusException for invalid argument`() {
        // cenário
        val mensagem = "Dados da requisição estão inválidos."
        val invalidArgumentException = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(mensagem))

        // ação
        val resposta = GlobalExceptionHandler().handle(requestGenerica, invalidArgumentException)

        // validação
        with(resposta) {
            assertEquals(HttpStatus.BAD_REQUEST, this.status)
            assertNotNull(this.body())
            assertEquals(mensagem, (this.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 500 quando qualquer outro erro for lancado`() {
        // cenário
        val internalErrorException = StatusRuntimeException(Status.INTERNAL)

        // ação
        val resposta = GlobalExceptionHandler().handle(requestGenerica, internalErrorException)

        // validação
        with(resposta) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.status)
            assertNotNull(this.body())
            assertTrue((this.body() as JsonError).message.contains("INTERNAL"))
        }
    }

}