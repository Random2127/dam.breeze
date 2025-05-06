# Breeze 🎫

**Breeze** es una aplicación Android pensada para facilitar la creación y gestión de eventos, tanto para organizadores como para asistentes.  
Permite ver eventos disponibles, registrarse, adquirir tickets y crear nuevos eventos desde el mismo dispositivo.

## 🛠 Tecnologías utilizadas

- Android SDK
- Java
- Firebase (para base de datos y autenticación)

## 👤 Roles de usuario

- **Cliente:** Puede explorar eventos, filtrarlos por ubicación y adquirir entradas (no hay sistema de pago implementado).
- **Organizador:** Puede crear y gestionar sus propios eventos.

## ✅ Funcionalidades

- Registro y login de usuarios
- Pantallas según el rol
- Visualización y creación de eventos
- Sistema de adquisición de tickets
- Funcionalidades planeadas: chat por evento y feedback

## 🗃️ Estructura básica de la base de datos

```plaintext
user: { id, nombre, email, role }
evento: { id, name, descripción, ubicación, fecha, hora, capacidad, precio, imagen, organizadorId }
ticket: { id, userId, eventoId, used, fechaCompra }
feedback: { id, clienteId, eventoId, comentario, rating } // Planeado
