<#import "../parts/common.ftl" as c>
<@c.page "Welcome Page">
<div class="row">

      <div class="col-lg-3">

        <h1 class="my-4">Book Shop</h1>
        <#list AllGenres as genre>
        <div class="list-group">
          <a href="#" class="list-group-item">${genre}</a>
        </div>
   		</#list>

      </div>
      <!-- /.col-lg-3 -->

      <div class="col-lg-9">

        <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
          </ol>
          <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
            </div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
            </div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
            </div>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>

        <div class="row">
        <!--  FIRST FIVE BOOKS  -->
		<#list books as book>
          <div class="col-lg-4 col-md-4 mb-6">
            <div class="card h-100">
              <a href="#"><img class="card-img-top" src="<#if book.bookCoverFileName??>
              												images/${book.bookCoverFileName} 
              											<#else> 
              												images/no-image.jpg 
              											</#if>" height="300"   alt=""></a>
              <div class="card-body">
                <h4 class="card-title">
                  <a href="#">${book.name}</a>
                </h4>
                <p class="card-text">${book.information}</p>
              </div>
            </div>
          </div>
		</#list>
		
        </div>
        <!-- /.row -->

      </div>
      <!-- /.col-lg-9 -->

    </div>

</@c.page>

