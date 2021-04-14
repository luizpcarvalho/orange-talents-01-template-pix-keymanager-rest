package br.com.zup.edu.keymanager.registra

import br.com.zup.edu.KeyManagerRegistraServiceGrpc
import br.com.zup.edu.RegistrarChavePixResponse
import br.com.zup.edu.keymanager.shared.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChavePixControllerTest {

    @field:Inject
    lateinit var registraStub: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve registrar uma nova chave pix`() {
        // cenário
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RegistrarChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

        given(registraStub.registra(any())).willReturn(respostaGrpc)

        val novaChavePix = NovaChavePixRequest(
            tipoConta = TipoContaRequest.CONTA_CORRENTE,
            chave = "luiz@gmail.com",
            tipoChave = TipoChaveRequest.EMAIL
        )

        // ação
        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChavePix)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)

        // validação
        with(response) {
            assertEquals(HttpStatus.CREATED, this.status)
            assertTrue(this.headers.contains("Location"))
            assertTrue(this.header("Location")!!.contains(pixId))
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = mock(KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub::class.java)
    }

}