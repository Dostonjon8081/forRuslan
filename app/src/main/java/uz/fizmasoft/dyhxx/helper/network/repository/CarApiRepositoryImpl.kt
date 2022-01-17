package uz.fizmasoft.dyhxx.helper.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.dataSource.ICarApiDataSource
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.helper.util.logd
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel
import javax.inject.Inject

class CarApiRepositoryImpl @Inject constructor(private val iCarApiDataSource: ICarApiDataSource) :
    BaseApiResponse(), ICarApiRepository {

    override suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<String>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.saveCar(saveCarModel)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun allCars(token: String): Flow<NetworkResult<List<AllCarsResponseModel>>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.allCars(token)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteCar(carNumber: String): Flow<NetworkResult<String>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.deleteCar(carNumber)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getViolation(
        carNumber: String,
        texPass: String
    ): Flow<NetworkResult<List<ViolationCarApiModelResponse>>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.getViolation(carNumber, texPass)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Flow<NetworkResult<ViolationPDFResponseModel>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.getPdfFile(violationPDFModel)
            })
        }.flowOn(Dispatchers.IO)
    }
}