package br.com.zup.edu.keymanager.busca

import br.com.zup.edu.ConsultaChavePixRequest
import br.com.zup.edu.KeyManagerConsultaServiceGrpc
import br.com.zup.edu.KeyManagerListaServiceGrpc
import br.com.zup.edu.ListaChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class ConsultaChavePixController(
    private val consultaChavePixClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub,
    private val listaChavesPixClient: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun consulta(clienteId: UUID, pixId: UUID): HttpResponse<Any> {

        LOGGER.info("[$clienteId] consultando a chave pix $pixId")
        val response = consultaChavePixClient.consulta(
            ConsultaChavePixRequest.newBuilder()
                .setPixId(
                    ConsultaChavePixRequest.FiltroPorPixId.newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId.toString())
                )
                .build()
        )

        return HttpResponse.ok(DetalheChavePixResponse(response))

    }

    @Get("/pix")
    fun lista(clienteId: UUID): HttpResponse<Any> {

        LOGGER.info("[$clienteId] consultando a lista de chaves Pix")

        val response = listaChavesPixClient.lista(ListaChavePixRequest.newBuilder()
                                                                                    .setClienteId(clienteId.toString())
                                                                                    .build())

        val chaves = response.chavesList.map { ListaChavesPixResponse(it) }
        return HttpResponse.ok(chaves)

    }

}