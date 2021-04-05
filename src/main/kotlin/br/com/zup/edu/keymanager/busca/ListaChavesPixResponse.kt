package br.com.zup.edu.keymanager.busca

import br.com.zup.edu.ListaChavePixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ListaChavesPixResponse(response: ListaChavePixResponse.ChavePixDetails) {

    val id = response.pixId
    val chave = response.valorChave
    val tipo = response.tipoChave
    val tipoConta = response.tipoConta
    val criadaEm = response.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

}
