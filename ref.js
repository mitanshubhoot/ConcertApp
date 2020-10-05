var express=require("express");
var bodyParser = require('body-parser');
var router=express.Router();
var Reference=require("../models/ref");
var Upload=require("../models/upload");
var middleware=require("../middleware");

//show all games
router.get("/",middleware.isLoggedIn,function(req,res){
    
    Reference.find({},function(err,ref){
        var flag=2;
        if(err){
        console.log("SOMETHING WENT WRONG");
        }else{
        if(req.isAuthenticated()){

               console.log(req.user.username);
                   if(req.user.username==="deva@gmail.com"){
                        flag=1;
                    
                        
                 }}
        res.render("ref/index",{flag:flag,ref:ref});
        }
        });
        
    
 
});

router.post("/",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    //get data from form and add to db
    var name=req.body.name;
    var image=req.body.image;
    var Videoslink=req.body.Videoslink;
    var hide=false;
    
    var author={
        id:req.user._id,
        username:req.user.username
    }
    var newreference={name:name,image:image,author:author,Videoslink:Videoslink,hide:hide};
    //create a new campground and add db
    Reference.create(newreference,function(err,ref){
        if(err){
            console.log(err);
            }else{
                res.redirect("/ref");
            }
    });
 });


router.get("/new",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    res.render("ref/new");
 });


 router.post("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
     var flag=2;
    Reference.findById(req.params.id,function(err,ref){
        if(err){
            console.log(err);
        }else{
            ref.hide=true;
            Reference.findByIdAndUpdate(req.params.id,ref.hide,function(err,updatedReference){
                if(err){
                    console.log(err);
                    }else{
                        
                        console.log(updatedReference.hide);

                    }
            })  ; 
            Reference.find({},function(err,ref){
                var flag=2;
                if(err){
                console.log("SOMETHING WENT WRONG");
                }else{
                if(req.isAuthenticated()){
                       console.log(req.user.username);
                           if(req.user.username==="deva@gmail.com"){
                                flag=1;
                            
                                
                         }}
                res.render("ref/index",{flag:flag,ref:ref});
                }
                });
                
        }

    });
 });


 router.get("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
     
 Reference.findById(req.params.id,function(err,ref){
     var flag=2;
    if(err){
        console.log(err);
    }else{
        if(req.isAuthenticated()){
            console.log(req.user.username);
                if(req.user.username==="deva@gmail.com"){
                     flag=1;
                                      
              }}
     res.render("ref/show",{flag:flag,ref:ref});
            // console.log(games);
            // res.render("games/show",{games:games});
     }
}); 
});
router.get("/:id/play",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    console.log(req.params.id);
    Reference.findById(req.params.id).populate("contents").exec(function(err,ref){
       if(err){
           console.log(err);
       }else{
               
               res.render("ref/play",{ref:ref});
        }
   }); 
   });



   router.delete("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    Reference.findByIdAndDelete(req.params.id,function(err){
        if(err){
            console.log(err);
        }else{
           res.redirect("/ref");
        }
    })
});

router.put("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){

    Reference.findByIdAndUpdate(req.params.id,req.body.Reference,function(err,updatedReference){
        if(err){
            console.log(err);
            }else{
                res.redirect("/ref");
            }
    })  ; 
    });

    router.put("/:id/upload",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    var flag=2;
        Reference.findByIdAndUpdate(req.params.id,req.body.ref,function(err,updatedReference){
            if(err){
                console.log(err);
                }else{
                        
                        res.redirect("back");
                        
                        console.log(flag);
                        
                }
        })  ; 
        });

router.get("/:id/edit",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){

    Reference.findById(req.params.id,function(err,foundReference){
        
                    res.render("ref/edit",{reference:foundReference});
    });
    
});

module.exports=router;