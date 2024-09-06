export function parseJwt(token) {
    if (!token) { return }
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace('-', '+').replace('_', '/')
    return JSON.parse(window.atob(base64))
  }
  
  export function getSocialLoginUrl(name) {
    return `http://localhost:8080/oauth2/authorization/${name}?redirect_uri=http://localhost:5173/oauth2/redirect`
  }
  
  export const handleLogError = (error) => {
    if (error.response) {
      console.log(error.response.data)
    } else if (error.request) {
      console.log(error.request)
    } else {
      console.log(error.message)
    }
  }