package br.com.zup.edu.keymanager

import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(private val removeChavePixClient
                               : KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun remove(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOGGER.info("[$clienteId] removendo uma chave pix com pixId: $pixId")
        removeChavePixClient.remove(RemoveChavePixRequest.newBuilder()
                                                         .setClienteId(clienteId.toString())
                                                         .setPixId(pixId.toString())
                                                         .build())

        return HttpResponse.ok()
    }

}