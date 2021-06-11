const Book=require('./Book.js');
var array=new Array();

class BookDAO{

    //Getter
    get get_ArrayBooks(){
        return array;
    } 
    //ADD BOOK
    add(title,id,author){
        var i;
        var book;
        var flag;
        if(array.length <1){
            book = new Book(title,id,author);
            array.push(book);
            return true;
        }else{
            for(i=0;i<array.length;i++){
                if(id == array[i].id){
                    flag=true;
                    return false;
                }
            }
            if(flag !== true){
                book = new Book(title,id,author);
                array.push(book);
                return true;
            }
        }
    }//end of add


    //DELETE BOOK
    delete(id){
        var i;
        for(i=0;i<array.length;i++){
            if(id == array[i].id){
                array.splice(i,1)
                return true;
            }
        }
    }//end of delete

    edit(title,id,author,review){
        var i;
        for(i=0;i<array.length;i++){
            if(array[i].get_Id == id){
                array[i].set_Title(title)
                array[i].set_Author(author)
                array[i].set_Review(review)
            }
        }
    }
}// end of class

module.exports = BookDAO