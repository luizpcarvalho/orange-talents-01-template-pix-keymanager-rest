package br.com.zup.edu.keymanager

import br.com.zup.edu.ListaChavePixResponse

class ListaChavesPixResponse(response: ListaChavePixResponse) {

    val clienteId = response.clienteId
    val chaves = response.chavesList

}

class Chaves {

    fun listaChaves(chaves: ListaChavePixResponse.ChavePixDetails): MutableMap<String, String> {
        return mapOf(Pair())
    }

}
