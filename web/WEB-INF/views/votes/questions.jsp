<%@ include file="../includes/headervotes.jsp" %>
<div class="media-body">
      <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
       
        <li>Votes</li>
    </ul>
    <h4> Member</h4>
     </div>
     <%@include file="../includes/toprightvotes.jsp" %>
</div>

<div class="contentpanel">
    <div class="row">
                           <div class="col-md-8">
                                <h5 class="lg-title"></h5>
                                <p class="mb20"></p>

                                <!-- BASIC WIZARD -->
                                <form:form method="post" action="saveVotes.htm" id="basicWizard" class="panel-wizard" modelAttribute="membervote" >
                                    
                               
                                    <ul class="nav nav-justified nav-wizard">
                                        
                                         <c:forEach var="i" begin="1" end="${agmvotesession.noofpages}">
	                                    <c:set var="tablink" value="#tab${i}"/>
                                            <c:set var="tabtitle" value="${i}"/>
                                            <li><a href="${tablink}" data-toggle="tab"><strong>${tabtitle}</strong> </a></li>
                                        </c:forEach>
                                    </ul>
                
                                    <div class="tab-content">
                                        
                                        
                                        <c:forEach var="j" begin="1" end="${agmvotesession.noofpages}">
	                                    <c:set var="tabjlink" value="tab${j}"/>
                                           
                                            <div class="tab-pane" id="${tabjlink}">
                                                
                                               <div class="error">
                                                  <form:errors path="extrafldStr" />     
                                               </div>
                                                  <c:forEach items="${agmvotesession.questaggregate}"  var="dvote" varStatus="status">
                                                    
                                                     <c:if test="${dvote.key eq j}">
                                                          <c:set var="dvotedetails" value="${dvote.value}"/>
                                                         <h5 class="lg-title">${dvotedetails.description}</h5> 
                                                         <input type="hidden" name="dvotedetails:::${dvotedetails.id}" value="${dvotedetails.id}" />
                                                         
                                                         <input type="hidden" name="maxopt${j}" value="${dvotedetails.maxoption}" id="maxopt${j}" />
                                                         <input type="hidden" name="answertype${j}" value="${dvotedetails.electionanswertypeid}" id="answertype${j}"/>
                                                        <!-- set options - radio or checkbox -->
                                                         
                                                         
                                                         
                                                         <c:forEach var="optionitem" items="${dvotedetails.voteoptions}">
    							<div class="form-group">
	                                                <label class="col-sm-4 control-label tooltips" data-original-title="${optionitem.description}" data-toggle="tooltip" data-placement="left">${optionitem.description} </label>
	                                                <div class="col-sm-8">
	                                                     <c:set var="query2" value="opt:::${dvotedetails.id}"/>
                                                             <c:set var="query3" value="opt${dvotedetails.id}"/>
	                                                     <c:choose>
                                                                 <c:when test="${dvotedetails.electionanswertypeid == 2}">
                                                                     <input id="${optionitem.id}" name="${query2}" type="checkbox" value="${optionitem.id}" class="form-control" />
                                                                 </c:when>
                                                                 <c:otherwise>
                                                                     <input id="${optionitem.id}" name="${query2}" type="radio" value="${optionitem.id}" class="form-control" />
                                                                 </c:otherwise>
                                                             </c:choose>                           
															
															
	                                                </div>
	                                            </div><!-- form-group -->
                                            
    						   </c:forEach>
                                                         
                                                         
                                                                                            
                                                     </c:if>
                                          </c:forEach>
    					 </div>
                                     </c:forEach>
                                        
                                        
                                        
                                      
                                        
                                    </div><!-- tab-content -->
                
                                    <ul class="list-unstyled wizard">
                                    
                                         <input type="hidden" name="nooftabs" value="${agmvotesession.noofpages}" />
                                         <input type="hidden" name="stoppoint"  id="stoppoint" value="${agmvotesession.prevpagestart}" />
                                        <li class="pull-left previous"><button type="button" class="btn btn-default">Previous</button></li>
                                        <li class="pull-right next"><button type="button" class="btn btn-danger">Next</button></li>
                                        <li class="pull-right finish hide"><button type="submit" class="btn btn-danger">Finish</button></li>
                                    </ul>
                                    
                                </form:form><!-- panel-wizard -->
              
                            </div><!-- col-md-6 -->
    </div><!-- row -->
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  

 <script>
            jQuery(document).ready(function() {
                
                // This will empty first option in select to enable placeholder
                //jQuery('select option:first-child').text('');
                
                // Select2
                jQuery("select").select2({
                    minimumResultsForSearch: -1
                });
                
                jQuery('#datepicker').datepicker();
                $.datepicker.setDefaults({
                   dateFormat: 'dd/mm/yy'
                });
                
                 // Basic Wizard
                jQuery('#basicWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        //var $stoppoint = $("#stoppoint").val();
                         
                        if($current >= $total) {
                            $('#basicWizard').find('.wizard .next').addClass('hide');
                            $('#basicWizard').find('.wizard .finish').removeClass('hide');
                            
                        } else {
                            $('#basicWizard').find('.wizard .next').removeClass('hide');
                            $('#basicWizard').find('.wizard .finish').addClass('hide');
                            var $stoppoint = $('#stoppoint').val();
                             //if($current <= $stoppoint) {
                            //    $('#basicWizard').find('.wizard .previous').addClass('hide');
                            //}
                            //else
                            //{
                            //    $('#basicWizard').find('.wizard .previous').removeClass('hide');
                            //} 
                            if($current < 2) {
                              $('#basicWizard').find('.wizard .previous').addClass('hide');
                            }
                            else {
                              $('#basicWizard').find('.wizard .previous').removeClass('hide');
                            }
                        }
                    },
                    onTabClick: function(tab, navigation, index) {
                      return false;
                     }
                     
                     ,onNext: function(tab, navigation, index) {
                        var $valid = jQuery('#basicWizard').valid();
                        if (!$valid) {
                            $validator.focusInvalid();
                            return false;
                        }
                       
                     var $obj =  $('input[name^="opt"]'); 
                      var $dcurrent = index;
                      //console.log("$dcurrent.toString() " + $dcurrent.toString());
                      var $dtab = '#tab' + $dcurrent.toString();
                      var $objsub = $($dtab).find($obj);
        
                       var $arr = $objsub.map(function () {
                          return this.value; // $(this).val()
                        }).get();
                        var $arr2 = $objsub.map(function () {
                          return this.id; // $(this).val()
                        }).get();
                        //console.log("array " + $arr.length);
                        var $totalx=0;
                        for(var $i=0;$i<$arr2.length;$i++){
                           if ($("#" + $arr2[$i]).prop('checked')===true)  //if checked
                           {
                              $totalx = $totalx + 1;
                           }
                           
                       }
                      var $curranswertype = $('#answertype'+ $dcurrent.toString()).val();
                      var $currmaxlimit = $('#maxopt'+ $dcurrent.toString()).val();
                      //console.log("total " + $totalx + " answertyype - " + $curranswertype + " maxlim - " + $currmaxlimit);
                     
                         if($curranswertype==="2") 
                         {    
                          if($totalx > $currmaxlimit)
                           {  
                            alert("Total vote option selected is more than limit - " + $totalx + " > " + $currmaxlimit);   
                            //jQuery('#errorwithinput').removeClass('hide');   
                            return false; 
                           }
                         }  
                     }   
                });
                
                // Progress Wizard
                jQuery('#progressWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#progressWizard').find('.wizard .next').addClass('hide');
                            $('#progressWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#progressWizard').find('.wizard .next').removeClass('hide');
                            $('#progressWizard').find('.wizard .finish').addClass('hide');
                        }
                        
                        var $percent = ($current/$total) * 100;
                        $('#progressWizard').find('.progress-bar').css('width', $percent+'%');
                    }
                });
                
                // Wizard With Disabled Tab Click
                jQuery('#tabWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#tabWizard').find('.wizard .next').addClass('hide');
                            $('#tabWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#tabWizard').find('.wizard .next').removeClass('hide');
                            $('#tabWizard').find('.wizard .finish').addClass('hide');
                        }
                    },
                    onTabClick: function(tab, navigation, index) {
                        return false;
                    }
                });
                
                // Wizard With Form Validation
                jQuery('#valWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#valWizard').find('.wizard .next').addClass('hide');
                            $('#valWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#valWizard').find('.wizard .next').removeClass('hide');
                            $('#valWizard').find('.wizard .finish').addClass('hide');
                        }
                    },
                    onTabClick: function(tab, navigation, index) {
                        return false;
                    },
                    onNext: function(tab, navigation, index) {
                        var $valid = jQuery('#valWizard').valid();
                        if (!$valid) {
                            $validator.focusInvalid();
                            return false;
                        }
                    }
                });
                
                // Wizard With Form Validation
                var $validator = jQuery("#valWizard").validate({
                    highlight: function(element) {
                        jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                    },
                    success: function(element) {
                        jQuery(element).closest('.form-group').removeClass('has-error');
                    }
                });
                
              
                // This will submit the basicWizard form
                jQuery('.panel-wizard').submit(function() {    
                    var retVal = confirm('This will submit your votes and you will be logged out of the session. Click OK to cast your votes');
                    if( retVal == true )
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                    });

            });
        </script>
        
 <script type="text/javascript">
 
function ShowBox(idops, idtarget)
{
        $("#" + idops).val();
}
</script>




  
