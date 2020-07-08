Ambulance Transfer Service

Setup instructions:

1. Download code:
    - Backend: https://github.com/Samuel-Ruiz/prueba-grupo-asv
    - Frontend: https://github.com/Samuel-Ruiz/prueba-grupo-asv-dashboard
    
2. Build:
    - Frontend: 
    -       npm run build:prod
    - Backend:
    -       mvn package
    
3. Edit docker-compose:
 
    On the backend root folder, edit docker-compose.yaml

   -        "volumes.postgresql.driver_opts.device"
   -        "volumes.postgresql_data.driver_opts.device" 

    with any valid full path.

4. Docker compose
    
    On the backend root folder run:
    -       docker compose up
    
Wait till docker finish to compose everything, and then connect 
to localhost:8081 with your browser


