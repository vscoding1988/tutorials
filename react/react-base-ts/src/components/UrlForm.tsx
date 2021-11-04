import {CFormInput,CForm,CButton} from "@coreui/react";
import {Component} from "react";


class UrlForm extends Component {
  render() {
    return (
            <div className="form-wrapper">
              <CForm >
                <CFormInput placeholder="Enter target Url" className="p-2 m-2 w-100"/>
                <CFormInput placeholder="Enter short url" className="p-2 m-2 w-100"/>
                <CButton type="submit" className="w-100 p-2 m-2">Submit</CButton>
              </CForm>
            </div>

    );
  }
}
export default UrlForm;
