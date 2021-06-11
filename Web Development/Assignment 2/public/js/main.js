

//PX1 JAVASCRIPT CODE

function searchBooks(number){
  document.getElementById('main_id').innerHTML='Loading...';
  let myHeaders = new Headers()
  myHeaders.append('Accept','application/json')

  let init={
  method:"GET",
  headers: myHeaders
  }

  var key=document.getElementById('key').value

  url='https://reststop.randomhouse.com/resources/works?start=0&max='
  url=url.concat(number)
  url += '&search='
  var final_url=url.concat(key)


  fetch(final_url,init)
  .then(response => response.json())
  .then(data => {
    let output =`    
    <br>
    <button id="results" onclick="searchBooks(100)">Show 50 results</button>
    <button id="results" onclick="searchBooks(0)">Show all results</button>

    `

    var i;

    for( i=0; i<data.work.length; i++){
      let unedited_title=data.work[i].titleweb;
      let title = unedited_title.replace(/['']/g,"")
      let id = data.work[i].workid
      let author = data.work[i].authorweb

      if(title.toUpperCase().includes(key.toUpperCase())){
        var save_id= "save_"+data.work[i].workid
        var del_id= "del_"+data.work[i].workid

        output +=` 
                    <section>
                      <h3>${title}</h3>
                      <label id="lab">ID:</label>
                      <label class="val"> ${id}<label><br><br>
                      <label id="lab">Author:</label>
                      <label class="val"> ${author}</label><br><br>
                      <input type="image" src="images/add.png" width="35px" height="35px" id="${save_id}" onclick ="btn_save(${del_id},'${title}',${id},'${author}')"></input>
                      <input type="image" src="images/del.png" width="35px" height="35px" hidden id="${del_id}" onclick ="btn_del(${del_id},'${title}',${id},'${author}')"></input>
                    </section>
                        `;
      }
    }

  document.getElementById('main_id').innerHTML=output;

  })

  .catch((error)=>{
    document.getElementById('main_id').innerHTML='No results found';

  })



}

function fav_page(){
  window.location.href = '/static/favorites.html';
}


// PX2 JAVASCRIPT CODE

function btn_save(del_id,title,id,author){
  document.getElementById(del_id.id).hidden = false;
  sendJSON(del_id,title,id,author)

}

 function btn_del(del_id,title,id,author){
  deleteJSON(del_id,title,id,author)
} 

function btn_del_fav(del_id,title,id,author){
  deleteFav(del_id,title,id,author)
} 


//POST

function sendJSON(del_id,title,id,author){
  var obj = {title: title, id: id, author: author};
  var obj_json = JSON.stringify(obj);

  const options={
    method: 'POST',
    headers: {"Content-Type": "application/json"},
    body: obj_json
  }

  fetch('http://localhost:8080/save',options)
  .then(response=>{
    if(response.status === 201){
      document.getElementById(del_id.id).hidden = false;
    }else{
      alert('Book with ID : ' +id+ ' is duplicate')
    }
  });
}

//DELETE

function deleteJSON(del_id,title,id,author){
  var obj = {title: title, id: id, author: author};
  var obj_json = JSON.stringify(obj);
  
  const options={
    method: 'DELETE',
    headers: {"Content-Type": "application/json"},
    body: obj_json
  }
  fetch('http://localhost:8080/delete',options)
  .then(response=>{
    if(response.status === 201){
      document.getElementById(del_id.id).hidden = true;
    }
  });

}

function deleteFav(del_id,title,id,author){
  var obj = {title: title, id: id, author: author};
  var obj_json = JSON.stringify(obj);
  
  const options={
    method: 'DELETE',
    headers: {"Content-Type": "application/json"},
    body: obj_json
  }
  fetch('http://localhost:8080/delete',options)
  .then(response=>{
    if(response.status === 201){
      document.getElementById(del_id.id).hidden = true;
      document.getElementById(id).hidden = true;

    }
  });

}

// PX3 JAVASCRIPT CODE

function favoritesFunction(){
  const options={
    method:'GET',
    headers: {"Content-Type": "application/json"}
  }

  var output='';


  fetch('http://localhost:8080/array',options)
  .then(response => response.json())
  .then(data => {
    var i;

    for(i=0;i<data.length;i++){     
      var del_id_fav= "del_"+data[i].id
      var edit_id_fav= "edit_"+data[i].id
      var title=data[i].title
      var id=data[i].id     
      var author = data[i].author
      var review=data[i].review

      output +=` <section id=${data[i].id}> 
      <h3>${title}</h3>
        <label id="lab">ID:</label>
        <label class="val">${id}<label><br><br>
        <label id="lab">Author:</label> 
        <label class="val">${author}</label><br><br>
        <label id="lab">Review:</label> 
        <label class="val">${review}</label><br><br>
        <br>

      <input type="image" src="images/del.png" width="35px" height="35px"  id="${del_id_fav}" onclick ="btn_del_fav(${del_id_fav},'${title}',${id},'${author}')"></input>
      <input type="image" src="images/edit.png" width="35px" height="35px"  id="${edit_id_fav}" onclick="edit_pass_func(${id})"></input>

      <br><br>
      </section>
      `;
    }
    document.getElementById("main_fav_id").innerHTML=output

    if(output===""){
      document.getElementById("main_fav_id").innerHTML='Your list is empty!'
  }

  })
  return output;
}

function filter(){
  document.getElementById('main_fav_id').innerHTML='Loading...';
  myVar = setTimeout(filterBooks, 1000);
}

function filterBooks(){

  const options={
    method:'GET',
    headers: {"Content-Type": "application/json"}
  }
  fetch('http://localhost:8080/array',options)
  .then(response => response.json())
  .then(data => {
    var output=""
    var key_of_book=document.getElementById('searchFav').value
    for(i=0;i<data.length;i++){    
      var del_id_fav= "del_"+data[i].id
      var edit_id_fav= "edit_"+data[i].id
      var title=data[i].title
      var id=data[i].id     
      var author = data[i].author
      var review=data[i].review
      if(data[i].title.toUpperCase().includes(key_of_book.toUpperCase())){
        output +=` <div id=${data[i].id}> 
        <h3>${title}</h3>
        <label id="lab">ID:</label>
        <label class="val">${id}<label><br><br>
        <label id="lab">Author:</label> 
        <label class="val">${author}</label><br><br>
        <label id="lab">Review:</label> 
        <label class="val">${review}</label><br><br>
        <br>

      
        <input type="image" src="images/del.png" width="35px" height="35px"  id="${del_id_fav}" onclick ="btn_del_fav(${del_id_fav},'${title}',${id},'${author}')"></input>
      <input type="image" src="images/edit.png" width="35px" height="35px"  id="${edit_id_fav}" onclick="edit_pass_func(${id})"></input>

        <br><br>
        </div>
        `;
      } 
    }
    document.getElementById('main_fav_id').innerHTML=output;
  })
}


//Edit

function edit_pass_func(edit_id){
  window.location.href = '/static/edit.html';
  localStorage.setItem("edit_id",edit_id);
}

function editFunction(){

  const options={
    method:'GET',
    headers: {"Content-Type": "application/json"}
  }

  fetch('http://localhost:8080/array',options)
  .then(response => response.json())
  .then(data => {
    var i;
    for(i=0;i<data.length;i++){
      if(data[i].id == localStorage.getItem("edit_id")){
        /* console.log(data[i].title)
        console.log(data[i].author) */
        document.getElementById('title_value').value=data[i].title
        document.getElementById('author_value').value=data[i].author
        document.getElementById('id_value').value=data[i].id     
      }
    } 
  });
}

function save_changes(id){
  var new_title=document.getElementById('title_value').value
  var new_author =document.getElementById('author_value').value
  var new_review =document.getElementById('review_value').value


  var obj = {title: new_title, id: id, author: new_author , review:new_review};
  var obj_json = JSON.stringify(obj);

  const options={
    method:'POST',
    headers: {"Content-Type": "application/json"},
    body:obj_json
  }

fetch('http://localhost:8080/save_changes',options)
  .then(response => response.json())

}

function back_func(){
  window.location.href = 'http://localhost:8080/';

}