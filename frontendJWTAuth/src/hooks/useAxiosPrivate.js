import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
    const refresh = useRefreshToken();  // Funkcja do odświeżenia tokena
    const { auth } = useAuth();  // Aktualne dane uwierzytelniające

    useEffect(() => {
        const requestInterceptor = axiosPrivate.interceptors.request.use(
            (config) => {
                // Ustaw token w nagłówku Authorization przed każdym żądaniem
                if (!config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${auth?.accessToken}`;
                }
                return config;
            },
            (error) => Promise.reject(error)
        );

        const responseInterceptor = axiosPrivate.interceptors.response.use(
            response => response,
            async (error) => {
                const prevRequest = error?.config;

                // Sprawdzenie, czy wystąpił błąd 401 i czy zapytanie nie zostało już ponownie wysłane
                if (error?.response?.status === 401 && !prevRequest?.sent) {
                    prevRequest.sent = true;  // Flaga, aby uniknąć zapętlenia
                    // Pobierz nowy accessToken
                    const newAccessToken = await refresh();

                    // Ustaw nowy accessToken w nagłówku Authorization
                    prevRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;

                    // Ponów oryginalne zapytanie z nowym tokenem
                    return axiosPrivate(prevRequest);
                }

                return Promise.reject(error);
            }
        );

        // Czystka interceptorów podczas unmountowania komponentu
        return () => {
            axiosPrivate.interceptors.request.eject(requestInterceptor);
            axiosPrivate.interceptors.response.eject(responseInterceptor);
        };
    }, [auth, refresh]);

    return axiosPrivate;
}

export default useAxiosPrivate;
