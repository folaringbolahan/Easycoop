<%@ include file="../includes/header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Structure</li>
        </ul>
        <h4>Edit Account Structure</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

<p>
        <div class="row col-md-12">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="gl_pfeditaccountstruct.htm" modelAttribute="accountstructdet">  
          
            <div class="panel-body">

                
                    <div class="form-group">
                    <form:label path="acStrId" cssClass="col-sm-2 control-label">Id: </form:label>
                   <div class="col-sm-4"><form:input path="acStrId"  readonly="true" disabled="disable"/>
                        <div class="error">
                                <form:errors path="acStrId"/>
                   </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="structurecode" cssClass="col-sm-2 control-label tooltips"  data-original-title="Unique Code; can be Alphanumeric" data-toggle="tooltip" data-placement="left">Code: *</form:label>
                   <div class="col-sm-4"><form:input path="structurecode" size="20"  readonly="true" />
                        <div class="error">
                                <form:errors path="structurecode"/>
                   </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="description" cssClass="col-sm-2 control-label tooltips" data-original-title="Title" data-toggle="tooltip" data-placement="left">Description: *</form:label>
                   <div class="col-sm-4"><form:input path="description" size="50" />
                        <div class="error">
                                <form:errors path="description" />
                    </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="delimiter" cssClass="col-sm-2 control-label">Delimiter: </form:label>
                   <div class="col-sm-4"><form:select path="delimiter">
                         <form:option value="">None</form:option>
                         <form:option value="-">-</form:option>
                         <form:option value="/">/</form:option>
                        </form:select>
                    </div>
                       
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg1" cssClass="col-sm-2 control-label tooltips" data-original-title="First Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 1: *</form:label>
                    <div class="col-sm-4">
                       <form:select path="seg1">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg1" cssClass="error" />
                    </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg2" cssClass="col-sm-2 control-label tooltips" data-original-title="Second Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 2: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg2">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg2" cssClass="error" />
                    </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg3" cssClass="col-sm-2 control-label tooltips" data-original-title="Third Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 3: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg3">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg3" cssClass="error" />
                  </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg4" cssClass="col-sm-2 control-label tooltips" data-original-title="Fourth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 4: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg4">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg4" cssClass="error" />
                    </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg5" cssClass="col-sm-2 control-label tooltips" data-original-title="Fifth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 5: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg5">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg5" cssClass="error" />
                   </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg6" cssClass="col-sm-2 control-label tooltips" data-original-title="Sixth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 6: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg6">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg6" cssClass="error" />
                  </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                   <form:label path="seg7" cssClass="col-sm-2 control-label tooltips" data-original-title="Seventh Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 7: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg7">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg7" cssClass="error" />
                    </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg8" cssClass="col-sm-2 control-label tooltips" data-original-title="Eigth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 8: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg8">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg8" cssClass="error" />
                   </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg9" cssClass="col-sm-2 control-label tooltips" data-original-title="Ninth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 9: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg9">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg9" cssClass="error" />
                  </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                    <form:label path="seg10" cssClass="col-sm-2 control-label tooltips" data-original-title="Tenth Segment of Account No." data-toggle="tooltip" data-placement="left">Segment 10: </form:label>
                   <div class="col-sm-4">
                       <form:select path="seg10">
                           <form:option value="0" label="Choose Segment" />
                           <form:options  items="${accsegs}" itemValue="segmentid" itemLabel="name"/>
                       </form:select>
                        <div class="error">
                                <form:errors path="seg10" cssClass="error" />
                   </div>
                        </div>
                    </div><!-- form-group -->

                    <div>
               
                    <input type="submit"  class="btn btn-danger mr5"  value="Save"/>
                </div>
                </form:form>
            </div>
            </div> 
            
            <%@ include file="../includes/footer.jsp" %>  