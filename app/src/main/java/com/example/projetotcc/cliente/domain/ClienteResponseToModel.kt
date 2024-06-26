package com.example.projetotcc.cliente.domain

import com.example.projetotcc.cliente.data.remote.ClienteResponse

fun List<ClienteResponse>.toModel(): List<ClienteModel> {
    return map { cliente ->
        ClienteModel(
            id = cliente.id,
            idUsuario = cliente.idUsuario,
            nome = cliente.nome,
            telefone = cliente.telefone,
            enderecoPrincipal = cliente.enderecoPrincipal,
        )
    }
}

fun ClienteResponse.toModelNoList(): ClienteModel {
    return ClienteModel(
        id = this.id,
        idUsuario = this.idUsuario,
        nome = this.nome,
        telefone = this.telefone,
        enderecoPrincipal = this.enderecoPrincipal,
    )
}