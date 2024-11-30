const express = require('express');
const bodyParser = require('body-parser');
const { Pool } = require('pg');
const cors = require('cors');

// Crear una instancia de Express
const app = express();
const port = 3000; // Puerto donde se ejecutará el servidor

// Configuración de la conexión a PostgreSQL
const pool = new Pool({
    user: 'postgres',         // Cambia esto a tu usuario de PostgreSQL
    host: 'localhost',        // Cambia esto si tu base de datos no está en localhost
    database: 'info_idgs101d', // Nombre de tu base de datos
    password: 'utNay',        // Tu contraseña de PostgreSQL
    port: 5432,               // Puerto por defecto de PostgreSQL
});

// Middleware
app.use(cors()); // Permitir solicitudes desde otros dominios
app.use(bodyParser.json()); // Parsear solicitudes con cuerpo JSON

// Ruta para insertar datos
app.post('/insert', async (req, res) => {
    const { nombres, primer_apellido, segundo_apellido, edad, fecha_nacimiento, hora, fecha_registro, estado } = req.body;

    try {
        const query = `
            INSERT INTO informacion (nombres, primer_apellido, segundo_apellido, edad, fecha_nacimiento, hora, fecha_registro, estado)
            VALUES ($1, $2, $3, $4, $5, $6, $7, $8) RETURNING *;
        `;
        const values = [nombres, primer_apellido, segundo_apellido, edad, fecha_nacimiento, hora, fecha_registro, estado];
        const result = await pool.query(query, values);
        res.json(result.rows[0]); // Devuelve la fila insertada
    } catch (error) {
        console.error(error);
        res.status(500).send('Error al insertar los datos');
    }
});

// Ruta para obtener todos los datos
app.get('/data', async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM informacion');
        res.json(result.rows); // Devuelve todas las filas
    } catch (error) {
        console.error(error);
        res.status(500).send('Error al obtener los datos');
    }
});

// Iniciar el servidor
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});
