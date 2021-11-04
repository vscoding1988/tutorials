import React, {useState} from "react";
import {
  CButton,
  CCollapse,
  CContainer,
  CForm,
  CFormInput,
  CNavbar,
  CNavbarBrand,
  CNavbarNav,
  CNavItem,
  CNavLink
} from "@coreui/react";
import {IUrlModel} from "./UrlTableRow";

const Navbar = () => {
  const ajaxUrl: string = "http://localhost:8080/api/urls";
  const [urls, setUrls] = useState<Array<IUrlModel>>([]);

  const updateUrls = function () {
    fetch(ajaxUrl).then(function (response) {
      return response.json();
    }).then(function (j) {
      setUrls(j);
    });
  }

  return (
          <CNavbar expand="lg" colorScheme="light" className="bg-light">
            <CContainer fluid>
                <CNavbarNav>
                  <CNavItem>
                    <CNavLink href="#" active>
                      Home
                    </CNavLink>
                  </CNavItem>
                  <CNavItem>
                    <CNavLink href="campaign">Campaign</CNavLink>
                  </CNavItem>
                  <CNavItem>
                    <CNavLink href="new">
                      Create new
                    </CNavLink>
                  </CNavItem>
                </CNavbarNav>
                <CForm className="d-flex">
                  <CFormInput type="search" className="me-2" placeholder="Search"/>
                  <CButton type="submit" color="success" variant="outline">
                    Search
                  </CButton>
                </CForm>
            </CContainer>
          </CNavbar>
  );
}
export default Navbar;
