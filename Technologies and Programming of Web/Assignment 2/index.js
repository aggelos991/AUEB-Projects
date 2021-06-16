
const BookDAO=require('./models/BookDAO.js');

const express = require('express')
const path = require('path')
const app = express()

const port = 8080

app.listen(port)

/* 
    Serve static content from directory "public",
    it will be accessible under path /static, 
    e.g. http://localhost:8080/static/index.html
*/
app.use('/static', express.static(__dirname + '/public'))

// parse url-encoded content from body
app.use(express.urlencoded({ extended: false }))

// parse application/json content from body
app.use(express.json())

app.use(express.static(path.join(__dirname + '/public')))


// serve index.html as content root
app.get('/', function(req, res){

    var options = {
        root: path.join(__dirname, 'public')
    }

    res.sendFile('index.html', options, function(err){
        console.log(err)
    })
})

app.get('/favorites', function(req, res){

    var options = {
        root: path.join(__dirname, 'public')
    }

    res.sendFile('favorites.html', options, function(err){
        console.log(err)
    })
})


app.get('/edit', function(req, res){

    var options = {
        root: path.join(__dirname, 'public')
    }

    res.sendFile('edit.html', options, function(err){
        console.log(err)
    })
})

var array=new Array()
var dao= new BookDAO();

//POST JSON DATA
app.post('/save',function(request,response){
    
    var msg = dao.add(request.body['title'],request.body['id'],request.body['author'])

    if(msg){
        response.status(201).send("Added Book with id : "+request.body['id'])
    }else{
        response.status(400).send('Book with id : ' +request.body['id']+ ' is duplicate')
    }

}) 

//DELETE BOOK
app.delete('/delete',function(request,response){

    var msg = dao.delete(request.body['id'])
    if(msg){
        response.status(201).send("Deleted Book with id : "+request.body['id'])
    }

})

app.get('/array',function(request,response){
    var array1=JSON.stringify(dao.get_ArrayBooks)
    response.send(array1)
})

app.post('/save_changes',function(request,response){
    dao.edit(request.body['title'],request.body['id'],request.body['author'],request.body['review'])
})