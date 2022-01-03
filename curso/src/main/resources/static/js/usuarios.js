// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
    //alert(1231312);
  $('#usuarios').DataTable();
});

function getHeaders(){
    return {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json'
                     'Authorization': localStorage.token
                   }
}

async function cargarUsuarios(){
  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()

  });
  const usuarios = await request.json();

  let usuariosHTML = "";


  for (let usuario of usuarios){
    let btnEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let usuarioHTML = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'
                        +usuario.email+'</td><td>'+usuario.tlf+'</td><td>'+btnEliminar+'</td></tr>';

    usuariosHTML += usuarioHTML;
  }
  //console.log(usuarios);
  document.querySelector('#usuarios tbody').outerHTML = usuariosHTML;

}

async function eliminarUsuario(id){

    if(!confirm('Desea eliminar este usuario? '+ id)){
        return;
    }
     const request = await fetch('api/usuarios/'+ id, {
        method: 'DELETE',
        headers: getHeaders()
      });

    location.reload();
}
