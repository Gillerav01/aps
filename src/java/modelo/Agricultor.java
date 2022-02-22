package modelo;

public class Agricultor {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String password;
    private String email;

    public Agricultor() {
    }

    public Agricultor(String nombre, String apellido, String dni, String password, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.password = password;
        this.email = email;
    }
    
    public Agricultor(int id, String nombre, String apellido, String dni, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
    }
    
    public Agricultor(int id, String nombre, String apellido, String dni, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.password = password;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "El agricultor tiene estas caracteristicas: " + "\n" +
               "|------------------------------------------------|\n" + 
               "| Nombre: " + this.nombre + "\n" +
               "| Apellido: " + this.apellido + "\n" + 
               "| DNI: " + this.dni + "\n" + 
               "| Email: " + this.email + "\n" + 
               "|------------------------------------------------|\n";
    }
}
