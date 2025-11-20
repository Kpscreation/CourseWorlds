$(document).ready(function () {
    let lastScrollTop = 0; 
    $(window).scroll(function () {
        let currentScroll = $(this).scrollTop();
        if (currentScroll > lastScrollTop) {                        
            onScrollDown();
        } else {                    
            onScrollUp();
        }

        lastScrollTop = currentScroll; 
    });

    function onScrollDown() {                      
        $("nav").css("transform","translateY(-70px)");        
        // $("nav").css("backdrop-filter","blur(0px)");                
    }

    function onScrollUp() {                        
        $("nav").css("transform","translateY(0px)");
        // $("nav").css("backdrop-filter","blur(10px)");                
    }
    $("#menu-i").click(function(){           
        $("#part-2").addClass("show");
    });
    $("#close-i").click(function(){
        $("#part-2").removeClass("show");
    });
    
});

// var tl = gsap.timeline()
// tl.from("#box-1",{
//     top:0,    
//     right:"2%",        
// })
// tl.to("#box-1",{    
//     left:0,
//     y:"70vh",
//     rotate:0,
//     scrollTrigger:{
//         trigger:"#page-1",
//         scroller:"body",
//         markers:true,
//         start: "0 0",        
//         // end:"120% 120%",
//         scrub:1,      
//         // pin:true,  
//     }
// })
// tl.from("#box-1",{    
//     rotate:"-50%",
//     scrollTrigger:{
//         trigger:"#page-2",
//         scroller:"body",
//         markers:true,
//         start: "30% 50%",        
//         scrub:1,    
//         pin:true    
//     }    
// })
// var tl2 = gsap.timeline()
// tl2.to("#box-1",{
//     top:"220vh",
//     left:"50vw",
//     scrollTrigger:{
//         trigger:"#page-2",
//         scroller:"body",
//         markers:true,
//         start: "20% 50%",        
//         scrub:1,        
//     }
// })