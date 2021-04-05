package br.com.zup.edu.keymanager

import br.com.caelum.stella.validation.CPFValidator
import br.com.zup.edu.RegistrarChavePixRequest
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NovaChavePixRequest(
    @field:NotNull val tipoConta: TipoContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoChave: TipoChaveRequest?
) {

    fun paraModeloGrpc(clienteId: UUID): RegistrarChavePixRequest {
        return RegistrarChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoConta(tipoConta?.atributoGrpc ?: TipoConta.CONTA_DESCONHECIDA)
            .setTipoChave(tipoChave?.atributoGrpc ?: TipoChave.CHAVE_DESCONHECIDA)
            .setValorChave(chave ?: "")
            .build()
    }

}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {
    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }
            return CPFValidator(false)
                .invalidMessagesFor(chave)
                .isEmpty()
        }
    },
    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?) = chave.isNullOrBlank() // não deve ser preenchida
    };
    abstract fun valida(chave: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}