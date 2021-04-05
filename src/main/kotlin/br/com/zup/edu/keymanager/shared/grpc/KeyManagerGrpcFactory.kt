package br.com.zup.edu.keymanager.shared.grpc

import br.com.zup.edu.KeyManagerConsultaServiceGrpc
import br.com.zup.edu.KeyManagerListaServiceGrpc
import br.com.zup.edu.KeyManagerRegistraServiceGrpc
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = KeyManagerRegistraServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChave() = KeyManagerConsultaServiceGrpc.newBlockingStub(channel)

}