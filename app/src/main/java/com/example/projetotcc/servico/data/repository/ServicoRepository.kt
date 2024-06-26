package com.example.projetotcc.servico.data.repository

import android.util.Log
import com.example.projetotcc.login.data.repository.LoginRepository
import com.example.projetotcc.network.RetrofitNetworkClient
import com.example.projetotcc.servico.data.Cliente
import com.example.projetotcc.servico.data.ServicoRequest
import com.example.projetotcc.servico.data.remote.ServicoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDate

class ServicoRepository {

    private val repository = RetrofitNetworkClient.createNetworkClient(
        baseUrl = "http://44.198.225.29:8080/solocraft/"
    ).create(ServicoService::class.java)

    private val loginRepository: LoginRepository by lazy {
        LoginRepository()
    }

    suspend fun criarServico(
        titulo: String,
        descricao: String,
        valorServico: Double,
        custoAtual: Double,
        dataInicio: String,
        enderecoServico: String,
        nome: String,
        telefone: String,
        enderecoCliente: String,
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val cliente = Cliente(
                    nome = nome,
                    telefone = telefone,
                    enderecoPrincipal = enderecoCliente
                )

                val response = repository.criarServico(
                    servicoRequest = ServicoRequest(
                        idUsuario = loginRepository.pegarId(),
                        titulo = titulo,
                        descricao = descricao,
                        valorServico = valorServico,
                        custoAtual = custoAtual,
                        custoSoma = null,
                        dataInicio = dataInicio,
                        enderecoServico = enderecoServico,
                        cliente = cliente
                    )
                )
                response.isSuccessful
            } catch (ex: Exception) {
                Log.e("criarServico", ex.message.toString())
                false
            }
        }
    }

    suspend fun adicionarCustoPorIdTarefa(servicoId: Int, custoSoma:Double): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.adicionarCustoPorIdTarefa(
                    servicoId, servicoRequest = ServicoRequest(
                        idUsuario = null,
                        titulo = null,
                        descricao = null,
                        valorServico = null,
                        custoAtual = null,
                        custoSoma = custoSoma,
                        dataInicio = null,
                        enderecoServico = null,
                        cliente = null
                    )
                )
                response.isSuccessful
            } catch (ex: Exception) {
                Log.e("adicionarCustoServicoPorIdTarefa", ex.message.toString())
                false
            }
        }
    }

    suspend fun deletarServicoPorId(servicoId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.deletarServicoPorId(
                    servicoId
                )
                response.isSuccessful
            } catch (ex: Exception) {
                Log.e("deletarServicoPorId", ex.message.toString())
                false
            }
        }
    }

    suspend fun finalizarTarefaPorId(servicoId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.finalizarTarefaPorId(
                    servicoId
                )
                response.isSuccessful
                true
            } catch (ex: Exception) {
                Log.e("finalizarServicoPorId", ex.message.toString())
                false
            }
        }
    }

    suspend fun buscarTarefaPorIdUsuario(): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasPorIdUsuario(
                    idUsuario = loginRepository.pegarId()
                )
                response.body() ?: listOf()
            } catch (ex: Exception) {
                Log.e("ListaDeServicos", ex.message.toString())
                listOf()
            }
        }
    }

    suspend fun buscarTarefasPendentesPorIdUsuario(): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasPendentesPorIdUsuario(
                    idUsuario = loginRepository.pegarId()
                )
                response.body()?: listOf()
            } catch (ex: Exception) {
                Log.e("ListaDeServicosPendentes", ex.message.toString())
                listOf()
            }
        }
    }

    suspend fun buscarTarefasPendentesPorIdCliente(idCliente: Int): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasPendentesPorIdCliente(
                    idCliente = idCliente
                )
                response.body()?: listOf()
            } catch (ex: Exception) {
                Log.e("ListaDeServicosPendentesDoCliente", ex.message.toString())
                listOf()
            }
        }
    }

    suspend fun buscarTarefasConcluidasPorIdUsuario(): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasConcluidasPorIdUsuario(
                    idUsuario = loginRepository.pegarId()
                )
                response.body()?: listOf()
            } catch (ex: Exception){
                Log.e("ListaDeServicosConcluidos", ex.message.toString())
                listOf()
            }
        }
    }

    suspend fun buscarTarefasConcluidasPorIdCliente(idCliente: Int): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasConcluidasPorIdCliente(
                    idCliente = idCliente
                )
                response.body()?: listOf()
            } catch (ex: Exception) {
                Log.e("ListaDeServicosConcluidosDoCliente", ex.message.toString())
                listOf()
            }
        }
    }


    suspend fun buscarTarefasPorIdCliente(clienteId: Long): List<ServicoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.buscarTarefasPorIdCliente(
                    clienteId
                )
                response.body() ?: listOf()
            } catch (ex: Exception) {
                Log.e("ServicosCliente", ex.message.toString())
                listOf()
            }
        }
    }

}