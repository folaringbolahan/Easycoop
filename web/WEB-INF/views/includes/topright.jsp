
<div style="float:right">
    <div class="headerprofile" >
        <div class="profile-right">
            <a class="pull-right profile-thumb" href="#">
                <img class="img-circle" src="${resourceUrl}/images/photos/${currrentuserServicex.currusercompany.id}.jpg" alt="" width="50px" height="50px">
            </a>
            <div class="media-body" >
                <h3 class="media-heading">${currrentuserServicex.currusercompany.name} </h3>
                <small class="text-muted">${currrentuserServicex.curruser.userName} ,</small>
                <small class="text-muted"> ${currrentuserServicex.curruser.accessLevel} .</small>
                <small class="text-muted">Postdate :${currrentuserServicex.currusercompany.postDate} ;</small>
                <small class="text-muted">Period :${currrentuserServicex.currusercompany.currentPeriod}, ${currrentuserServicex.currusercompany.currentYear}. </small>
                <br><small class="text-muted" style="color: red">${currrentuserServicex.currusercompany.startofDay} </small>
            </div>
        </div>
    </div>
</div>