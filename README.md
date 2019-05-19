Repositorio para challenge de mercado libre.

Definiciones:
- Host: www.zarpapp.com
- Endpoint: /api/search
- Metodos disponibles: POST, GET, DELETE
- POST: Se da de alta un nuevo termino en la base de datos de autocompletado.  El Body del request debe tener un termino de busca con el siguiente formato:
  ```json  
    {  
        "term" : <Termino>  
    }
  ```
  Ejemplo:
   ```
  curl -X POST -H "Content-Type: application/json" -d "{ \"term\" : \"Terminal\"}" https://www.zarpapp.com/api/search
  ``` 
  La api respondera el termino dado de alta con el siguiente formato:
  ```json
	{
		"id" : <id>,
		"term: <Termino>
	}
  ```
  en caso de que el termino ya exista, se devolvera el termino con el id existente, no se inserta uno nuevo.
  
- DELETE: Se determina el termino a eliminar con el id del mismo en la url del endpoint: 
    ```
    /api/search/<id>
    ```
  Ejemplo:
  ```
  curl -X DELETE http://www.zarpapp.com/api/search/6
  ```
  La respuesta tiene status 200 en caso de exito. Si el termino no exista no se considera error.
  
- GET: La busqueda se hace mediante un parametro llamado term: 
    ```
    /api/search?term=<Termino>
    ```
  Ejemplo:
    ``` 
  http://www.zarpapp.com/api/search?term=al
    ```
  La api respondera un json con una coleccion de terminos con el siguiente formato:
  ```json
    [
        {
            "id" : <id>,
            "term: <Termino>
        },
        {
            "id" : <id>,
            "term: <Termino>
        },
        .
        .
        .
    ]
   ```
- Tratamiento de errores:  En caso de error se devolvera un json describiendo el error con el siguiente formato:
  ```json
    { 
        "errors" :
            [
                {
                    "code" : <Codigo de error>,
                    "title" : <Titulo del error>,
                    "detail" : <Descripcion del error>,
                    "status" : <HTTPStatus>
                }
            ]
    }
  ```
