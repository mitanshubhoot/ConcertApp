var express=require("express");
var router=express.Router();
var multer = require('multer');
var upload = multer({ dest: 'public/images' });
var fs = require("fs");
var path = require("path");
var Game=require("../models/games");
var Upload=require("../models/upload");
var Reward=require("../models/reward");
var User=require("../models/user");
const user = require("../models/user");
var middleware=require("../middleware");
var upl=0;
var storage = multer.diskStorage({ 
    destination: function (req, file, cb) { 
  
        // Uploads is the Upload_folder_name 
        cb(null, "public/images") ;
    }, 
    filename: function (req, file, cb) { 
      cb(null, file.originalname); 
    } 
  }) ;

  const maxSize = 1 * 1000 * 1000; 
    
var upload = multer({  
    storage: storage, 
    limits: { fileSize: maxSize }, 
    fileFilter: function (req, file, cb){ 
        fs.readdirSync("public/images").forEach(fil => {
           
              if(file.originalname==fil){
                   upl=1;
              
            }
          });
        // Set the filetypes, it is optional 
        var filetypes = /jpeg|jpg|png|gif/; 
        var mimetype = filetypes.test(file.mimetype); 
  
        var extname = filetypes.test(path.extname( 
                    file.originalname).toLowerCase());
                     

                  
        if (mimetype && extname && upl==0) { 
            console.log("upl="+upl);
            return cb(null, true); 
        } 
      
        cb("Error: File upload only supports the "
                + "following filetypes - " + filetypes); 
                
      }  
  
// mypic is the name of file attribute 
}).array("myfiles",10);        

//show all games
router.get("/",middleware.isLoggedIn,function(req,res){
    
    Game.find({},function(err,games){
        var flag=2;
        if(err){
        console.log("SOMETHING WENT WRONG");
        }else{
        if(req.isAuthenticated()){
               console.log(req.user.username);
                   if(req.user.username==="deva@gmail.com"){
                        flag=1;
                    
                        
                 }}
                 //console.log(games[0].rewards);
        res.render("games/index",{flag:flag,games:games});
        }
        });
        
    
 
});

router.post("/upl",middleware.isLoggedIn,function(req,res){
  
    upload(req,res,function(err) { 
  
            if(err) { 
                console.log("upl="+upl);
                upl=0;
                console.log("upl="+upl);
                // ERROR occured (here it can be occured due 
                // to uploading image of size greater than 
                // 1MB or uploading different file type)
                req.flash("error","Either images uploaded doesn't support jpg,jpeg,png,gif type or same image name already exists ");

            res.redirect("/games/upload_img"); 
                // res.send(err) 
            } 
            else { 
      
                // SUCCESS, image successfully uploaded 
               console.log("Success, Image uploaded!") 
               res.redirect("/games");
            } 
        }) ;

});

router.get("/upload_img",function(req,res){
res.render("games/upload");
});

router.post("/",function(req,res){
    //get data from form and add to db
    var name=req.body.name;
    var background=req.body.background;
    var image=req.body.image;
    var hide=false;
    console.log(background);
    // upload(req,res,function(err) { 
  
    //     if(err) { 
  
    //         // ERROR occured (here it can be occured due 
    //         // to uploading image of size greater than 
    //         // 1MB or uploading different file type) 
    //         res.send(err) 
    //     } 
    //     else { 
  
    //         // SUCCESS, image successfully uploaded 
    //        console.log("Success, Image uploaded!") 
    //     } 
    // }) 
    console.log("file="+req.file);
    //  const tempPath = req.file.path;
    // console.log(background);
    // const targetPath = path.join(__dirname, "public/images/deploy.jpg");
    // fs.rename(tempPath, targetPath, err => {
    //   if (err) return handleError(err, res);});
    
      var author={
        id:req.user._id,
        username:req.user.username
    }
    var newgame={name:name,image:image,author:author,background:background,hide:hide};
    //create a new campground and add db
    Game.create(newgame,function(err,game){
        if(err){
            console.log(err);
            }else{
                res.redirect("/games");
            }
    });
 });


router.get("/new",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    res.render("games/new");
 });


 router.post("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
     var flag=2;
    Game.findById(req.params.id,function(err,games){
        if(err){
            console.log(err);
        }else{
            games.hide=true;
            Game.findByIdAndUpdate(req.params.id,games.hide,function(err,updatedGame){
                if(err){
                    console.log(err);
                    }else{
                        
                        console.log(updatedGame.hide);

                    }
            })  ; 
            Game.find({},function(err,games){
                var flag=2;
                if(err){
                console.log("SOMETHING WENT WRONG");
                }else{
                if(req.isAuthenticated()){
                       console.log(req.user.username);
                           if(req.user.username==="deva@gmail.com"){
                                flag=1;
                            
                                
                         }}
                res.render("games/index",{flag:flag,games:games});
                }
                });
                
        }

    });
 });


 router.get("/:id",function(req,res){
     
 Game.findById(req.params.id,function(err,games){
     var flag=2;
     var unlock=false;
    if(err){
        console.log(err);
    }else{
        if(req.isAuthenticated()){
            console.log(req.user.username);
                if(req.user.username==="deva@gmail.com"){
                     flag=1;
                                      
              }}
     res.render("games/show",{flag:flag,games:games,user:req.user});
            // console.log(games);
            // res.render("games/show",{games:games});
     }
}); 
});

router.get("/:id/play",function(req,res){
    console.log(req.params.id);
    Game.findById(req.params.id).populate("contents").exec(function(err,games){
       if(err){
           console.log(err);
       }else{
        reward=games.rewards;
        console.log(reward);
        Reward.findById(reward,function(err,rewards){
            if(err){
                console.log(err);
            }else{
                console.log(reward);
                console.log(games);
               // console.log(rewards.gameid==req.params.id);
               res.render("games/play",{games:games,rewards:rewards,user:req.user});
            }
        });
               
        }
   }); 
   });

   

   router.delete("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    Game.findByIdAndDelete(req.params.id,function(err,game){
        if(err){
            console.log(err);
        }else{
            reward=game.rewards;
            console.log(req.params.id);
            User.find({},function(err,users){
                if(err){
                    console.log(err);
                }
                else{
                                      
                    users.forEach(function(use){
                        console.log("length="+game.rewards.length)
                        if(game.rewards.length>0){
                        use.rewards.remove(reward);
                        use.save();
                    }});
                }
            });
            
            console.log(game.rewards[0]);
            Reward.findByIdAndDelete(reward,function(err,rewards){
                if(err){
                    console.log(err);
                }else{
                   // console.log(rewards.gameid==req.params.id);
                    res.redirect("/games");
                }
            });
          
        }
    })
});

router.put("/:id",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){

    Game.findByIdAndUpdate(req.params.id,req.body.game,function(err,updatedGame){
        if(err){
            console.log(err);
            }else{
                res.redirect("/games/");
            }
    })  ; 
    });

    router.put("/:id/upload",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){
    var flag=2;
        Game.findByIdAndUpdate(req.params.id,req.body.game,function(err,updatedGame){
            if(err){
                console.log(err);
                }else{
                    req.flash("success","you have uploaded the game successfully");
                        res.redirect("back");
                        
                        console.log(flag);
                        
                }
        })  ; 
        });

router.get("/:id/edit",middleware.isLoggedIn,middleware.checkOwnership,function(req,res){

    Game.findById(req.params.id,function(err,foundGame){
        
                    res.render("games/edit",{game:foundGame});
    });
    
});

module.exports=router;