package kr.co.morandi.backend.common.web.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response);
    }

    public static ApiResult<?> success() {
        return new ApiResult<>(true, null);
    }

    public static ApiResult<ApiError> error(Throwable throwable) {
        return new ApiResult<>(false, new ApiError(throwable));
    }

    public static ApiResult<ApiError> error(String message) {
        return new ApiResult<>(false, new ApiError(message));
    }

    @Getter
    public static class ApiError {
        private final String message;

        ApiError(Throwable throwable) {
            this(throwable.getMessage());
        }

        ApiError(String message) {
            this.message = message;
        }

    }

    @Getter
    public static class ApiResult<T> {
        private final boolean success;
        private final T response;

        private ApiResult(boolean success, T response) {
            this.success = success;
            this.response = response;
        }
    }
}
