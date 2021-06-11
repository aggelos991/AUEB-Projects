
class Book{
    constructor(title,id,author,review){
        this.title=title;
        this.id=id;
        this.author=author;
        this.review='Δεν εχει γραφτεί κριτική για αυτό το βιβλίο'
    }
    //Getters
     get get_Title(){
        return this.title;
    }
    get get_Id(){
        return this.id;
    }
    get get_Author(){
        return this.author;
    }

    get get_Review(){
        return this.review
    }


    //Setters
    
      set_Title(title){
        this.title=title;
    }

     set_Author(author){
        this.author=author;
    } 

     set_Review(review){
        this.review=review
    } 
}
module.exports = Book
