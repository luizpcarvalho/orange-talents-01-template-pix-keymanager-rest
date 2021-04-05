package br.com.zup.edu.keymanager.busca

import br.com.zup.edu.ConsultaChavePixResponse
import br.com.zup.edu.TipoConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChavePixResponse(response: ConsultaChavePixResponse) {

    val pixId = response.pixId
    val tipoChave = response.chave.tipo
    val chave = response.chave.chave

    val criadaEm = response.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

    val tipoConta = when(response.chave.conta.tipo) {
        TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "NAO_RECONHECIDA"
    }

    val conta = mapOf(Pair("tipo", tipoConta),
        Pair("instituicao", response.chave.conta.instituicao),
        Pair("nomeDoTitular", response.chave.conta.nomeDoTitular),
        Pair("cpfDoTitular", response.chave.conta.cpfDoTitular),
        Pair("agencia", response.chave.conta.agencia),
        Pair("numero", response.chave.conta.numeroDaConta)
    )

}
