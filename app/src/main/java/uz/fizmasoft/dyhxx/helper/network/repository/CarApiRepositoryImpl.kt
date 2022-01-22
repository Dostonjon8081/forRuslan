package uz.fizmasoft.dyhxx.helper.network.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.dataSource.ICarApiDataSource
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.helper.util.logd
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPDFResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPayModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.maps.ViolationMapApiResponseModel
import javax.inject.Inject

class CarApiRepositoryImpl @Inject constructor(private val iCarApiDataSource: ICarApiDataSource) :
    BaseApiResponse(), ICarApiRepository {

    override suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<String>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.saveCar(saveCarModel)
            })
        }.flowOn(IO)
    }

    override suspend fun violationPay(act: String): Flow<NetworkResult<ViolationPayModelResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.violationPay(act)
            })
        }
    }

    override suspend fun allCars(token: String): Flow<NetworkResult<List<AllCarsResponseModel>>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.allCars(token)
            })
        }.flowOn(IO)
    }

    override suspend fun deleteCar(carNumber: String): Flow<NetworkResult<String>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.deleteCar(carNumber)
            })
        }.flowOn(IO)
    }

    override suspend fun getViolation(
        carNumber: String,
        texPass: String
    ): Flow<NetworkResult<List<ViolationCarApiModelResponse>>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.getViolation(carNumber, texPass)
            })
        }.flowOn(IO)
    }

    override suspend fun violationPdf(violationId: Long): Flow<NetworkResult<ViolationPDFResponseModel>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.violationPdf(violationId)
            })
        }.flowOn(IO)
    }

    override suspend fun violationMap(eventId: String): Flow<NetworkResult<ViolationMapApiResponseModel>> {
        return flow {
            emit(
                safeApiCall {
                iCarApiDataSource.violationMap(eventId)
            }
        )
        }.flowOn(IO)
    }

}